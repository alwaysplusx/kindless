package com.kindless.moment.service.impl;

import com.harmony.umbrella.context.CurrentUser;
import com.harmony.umbrella.data.JpaQueryBuilder;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.harmony.umbrella.util.StringUtils;
import com.kindless.apis.client.WalletClient;
import com.kindless.apis.client.UserClient;
import com.kindless.apis.dto.*;
import com.kindless.apis.util.ResourceConverter;
import com.kindless.moment.domain.Moment;
import com.kindless.moment.domain.MomentResource;
import com.kindless.moment.repository.MomentRepository;
import com.kindless.moment.service.MomentService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuxii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MomentServiceImpl extends ServiceSupport<Moment, Long> implements MomentService {

    private final MomentRepository momentRepository;

    private final UserClient userClient;

    private final WalletClient userBalanceClient;

    @GlobalTransactional
    @Override
    public Moment push(MomentDto moment, CurrentUser user) {
        log.info("moment push in global transaction: {}", RootContext.getXID());

        String source = StringUtils.getFirstNotBlank(moment.getSource());

        Moment bePersist = new Moment();
        bePersist.setContent(moment.getContent());
        bePersist.setResourceSize(moment.getPictures().size());
        bePersist.setSource(source);
        bePersist.setType(moment.getType());
        bePersist.setStatus(0);
        bePersist.setUserId(user.getUserId());

        // 设置moment的资源
        List<MomentResource> resources = extractResources(moment);
        resources.forEach(e -> e.setMoment(bePersist));
        bePersist.setResources(resources);

        Moment result = momentRepository.save(bePersist);

        userBalanceClient.pay(
                PaymentDto
                        .builder()
                        .amount(BigDecimal.TEN)
                        .balanceType("cash")
                        .orderId(result.getId())
                        .orderType("moment")
                        .build()
        );

        return result;
    }

    @Override
    public MomentsDto getMoments(long cursor, CurrentUser user) {
        JpaQueryBuilder<Moment> builder = queryWith()
                .withPageable(PageRequest.of(0, 20));
        if (cursor > 0) {
            builder.lessThen("createdAt", new Date(cursor));
        }
        List<MomentDto> moments = builder
                .execute()
                .getListResult()
                .parallelStream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new MomentsDto(moments);
    }


    private List<MomentResource> extractResources(MomentDto moment) {
        List<MomentResource> resources = new ArrayList<>();
        List<ResourceDto> pictures = moment.getPictures();
        if (!CollectionUtils.isEmpty(pictures)) {
            pictures.forEach(e -> resources.add(ResourceConverter.momentPicture(e)));
        }
        List<ResourceDto> videos = moment.getVideos();
        if (!CollectionUtils.isEmpty(videos)) {
            videos.forEach(e -> resources.add(ResourceConverter.momentVideo(e)));
        }
        return resources;
    }

    @Override
    protected QueryableRepository<Moment, Long> getRepository() {
        return momentRepository;
    }

    @Override
    protected Class<Moment> getDomainClass() {
        return Moment.class;
    }

    private MomentDto convertToDto(Moment moment) {
        // TODO reaction webClient
        MomentDto dto = new MomentDto();
        dto.setContent(moment.getContent());
        dto.setSource(moment.getSource());
        List<ResourceDto> pictures = new ArrayList<>();
        List<ResourceDto> videos = new ArrayList<>();
        for (MomentResource res : moment.getResources()) {
            ResourceDto resDto = ResourceDto.toResource(res);
            if (res.getType() == ResourceDto.RESOURCE_OF_PICTURE) {
                pictures.add(resDto);
            }
            if (res.getType() == ResourceDto.RESOURCE_OF_VIDEO) {
                videos.add(resDto);
            }
        }
        dto.setPictures(pictures);
        dto.setVideos(videos);
        UserDto user = userClient.getUserById(moment.getUserId())
                .optionalData()
                .orElse(null);
        dto.setUser(user);
        dto.setCreatedAt(moment.getCreatedAt());
        return dto;
    }

}

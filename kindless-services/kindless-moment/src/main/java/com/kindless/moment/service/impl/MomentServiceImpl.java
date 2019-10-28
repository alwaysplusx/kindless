package com.kindless.moment.service.impl;

import com.kindless.apis.clients.UserClient;
import com.kindless.apis.domain.moment.Moment;
import com.kindless.apis.domain.moment.MomentResource;
import com.kindless.apis.dto.MomentDto;
import com.kindless.apis.dto.MomentsDto;
import com.kindless.apis.dto.ResourceDto;
import com.kindless.apis.dto.UserDto;
import com.kindless.apis.util.ResourceConverter;
import com.kindless.moment.repository.MomentRepository;
import com.kindless.moment.service.MomentService;
import com.harmony.umbrella.context.CurrentUser;
import com.harmony.umbrella.data.JpaQueryBuilder;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.harmony.umbrella.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuxii
 */
@Service
public class MomentServiceImpl extends ServiceSupport<Moment, Long> implements MomentService {

    @Autowired
    private MomentRepository momentRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public Moment push(MomentDto moment, CurrentUser user) {
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

        return momentRepository.save(bePersist);
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

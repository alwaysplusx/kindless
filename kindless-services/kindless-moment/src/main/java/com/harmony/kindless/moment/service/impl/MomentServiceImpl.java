package com.harmony.kindless.moment.service.impl;

import com.harmony.kindless.apis.domain.moment.Moment;
import com.harmony.kindless.apis.domain.moment.MomentResource;
import com.harmony.kindless.apis.dto.CurrentUser;
import com.harmony.kindless.apis.dto.MomentDto;
import com.harmony.kindless.apis.dto.ResourceDto;
import com.harmony.kindless.apis.util.ResourceConverter;
import com.harmony.kindless.moment.repository.MomentRepository;
import com.harmony.kindless.moment.service.MomentService;
import com.harmony.umbrella.data.repository.QueryableRepository;
import com.harmony.umbrella.data.service.ServiceSupport;
import com.harmony.umbrella.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuxii
 */
@Service
public class MomentServiceImpl extends ServiceSupport<Moment, Long> implements MomentService {

	private final MomentRepository momentRepository;

	@Autowired
	public MomentServiceImpl(MomentRepository momentRepository) {
		this.momentRepository = momentRepository;
	}

	@Override
	public Moment push(MomentDto moment, CurrentUser user) {
		String source = StringUtils.getFirstNotBlank(moment.getSource(), user.getDevice());

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
	public List<Moment> getMoments(CurrentUser user, Pageable pageable) {
		return queryWith()
				.withPageable(pageable)
				.execute()
				.getListResult();
	}

	private List<MomentResource> extractResources(MomentDto moment) {
		List<MomentResource> resources = new ArrayList<>();
		List<ResourceDto> pictures = moment.getPictures();
		if (!CollectionUtils.isEmpty(pictures)) {
			pictures.forEach(e -> resources.add(ResourceConverter.momentPicture(e)));
		}
		ResourceDto video = moment.getVideo();
		if (video != null) {
			resources.add(ResourceConverter.momentVideo(video));
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
}

package com.harmony.kindless.moment.service.impl;

import com.harmony.kindless.apis.domain.moment.Moment;
import com.harmony.kindless.apis.dto.CurrentUser;
import com.harmony.kindless.apis.dto.MomentDto;
import com.harmony.kindless.moment.repository.MomentRepository;
import com.harmony.kindless.moment.service.MomentService;
import com.harmony.umbrella.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxii
 */
@Service
public class MomentServiceImpl implements MomentService {

    private final MomentRepository momentRepository;

    @Autowired
    public MomentServiceImpl(MomentRepository momentRepository) {
        this.momentRepository = momentRepository;
    }

    @Override
    public Moment push(MomentDto moment, CurrentUser user) {
        String source = StringUtils.getFirstNotBlank(moment.getSource(), user.getDevice());
        Moment bePersistMoment = new Moment();
        bePersistMoment.setContent(moment.getContent());
        bePersistMoment.setResourceSize(moment.getPictures().size());
        bePersistMoment.setSource(source);
        bePersistMoment.setType(moment.getType());
        bePersistMoment.setStatus(0);
        return momentRepository.save(bePersistMoment);
    }

}

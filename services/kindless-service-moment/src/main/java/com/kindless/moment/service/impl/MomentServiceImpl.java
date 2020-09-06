package com.kindless.moment.service.impl;

import com.kindless.core.service.ServiceSupport;
import com.kindless.domain.moment.Moment;
import com.kindless.domain.user.User;
import com.kindless.moment.repository.MomentRepository;
import com.kindless.moment.service.MomentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

/**
 * @author wuxin
 */
@Service
@RequiredArgsConstructor
public class MomentServiceImpl extends ServiceSupport<Moment> implements MomentService {

    private final MomentRepository momentRepository;

    @Override
    public Moment publish(Moment moment) {
        return null;
    }

    @Override
    public Page<Moment> getUserTimeline(User user, PageRequest request) {
        return null;
    }

    @Override
    protected PagingAndSortingRepository<Moment, Long> getRepository() {
        return momentRepository;
    }

}

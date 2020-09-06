package com.kindless.moment.service;

import com.kindless.domain.moment.Moment;
import com.kindless.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author wuxin
 */
public interface MomentService {

    Moment publish(Moment moment);

    Page<Moment> getUserTimeline(User user, PageRequest request);

}

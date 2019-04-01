package com.harmony.kindless.moment.service;

import com.harmony.kindless.apis.domain.moment.Moment;
import com.harmony.kindless.apis.dto.MomentDto;
import com.harmony.umbrella.context.CurrentUser;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wuxii
 */
public interface MomentService {

    Moment push(CurrentUser user, MomentDto moment);

    List<Moment> getMoments(CurrentUser user, Pageable pageable);

}

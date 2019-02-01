package com.harmony.kindless.moment.service;

import com.harmony.kindless.apis.domain.moment.Moment;
import com.harmony.kindless.apis.dto.CurrentUser;
import com.harmony.kindless.apis.dto.MomentDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wuxii
 */
public interface MomentService {

	Moment push(MomentDto moment, CurrentUser user);

	List<Moment> getMoments(CurrentUser user, Pageable pageable);

}

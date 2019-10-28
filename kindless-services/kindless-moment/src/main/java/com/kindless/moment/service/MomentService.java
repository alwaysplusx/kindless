package com.kindless.moment.service;

import com.kindless.moment.domain.Moment;
import com.kindless.apis.dto.MomentDto;
import com.kindless.apis.dto.MomentsDto;
import com.harmony.umbrella.context.CurrentUser;

/**
 * @author wuxii
 */
public interface MomentService {

	Moment push(MomentDto moment, CurrentUser user);

	MomentsDto getMoments(long cursor, CurrentUser user);

}

package com.harmony.kindless.moment.service;

import com.harmony.kindless.apis.domain.moment.Moment;
import com.harmony.kindless.apis.dto.MomentDto;
import com.harmony.kindless.apis.dto.MomentsDto;
import com.harmony.umbrella.context.CurrentUser;

/**
 * @author wuxii
 */
public interface MomentService {

	Moment push(MomentDto moment, CurrentUser user);

	MomentsDto getMoments(long cursor, CurrentUser user);

}

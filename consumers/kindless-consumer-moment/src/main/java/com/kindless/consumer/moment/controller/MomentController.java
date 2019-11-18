package com.kindless.consumer.moment.controller;

import com.harmony.kindless.core.dto.IdDto;
import com.harmony.umbrella.context.CurrentUser;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.kindless.consumer.moment.dto.MomentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wuxii
 */
@BundleController
@RequiredArgsConstructor
public class MomentController {

    @PostMapping("/moment/push")
    public Response<IdDto> push(CurrentUser user, @RequestBody MomentDto moment) {
        // Moment result = momentService.push(moment, user);
        return Response.ok(IdDto.of(1L));
    }

}

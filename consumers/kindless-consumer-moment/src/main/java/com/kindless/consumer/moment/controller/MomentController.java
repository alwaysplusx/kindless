package com.kindless.consumer.moment.controller;

import com.harmony.kindless.core.dto.IdDto;
import com.harmony.umbrella.context.CurrentUser;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;
import com.kindless.moment.domain.Moment;
import com.kindless.consumer.moment.controller.dto.MomentDto;
import com.kindless.consumer.moment.dto.MomentsDto;
import com.kindless.moment.service.MomentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxii
 */
@BundleController
@RequiredArgsConstructor
public class MomentController {

    private final MomentService momentService;

    @PostMapping("/moment/push")
    public Response<IdDto> push(CurrentUser user, @RequestBody MomentDto moment) {
        Moment result = momentService.push(moment, user);
        return Response.ok(IdDto.of(result));
    }

    @BundleView({"new", "id"})
    @GetMapping("/moments")
    public Response<MomentsDto> moments(@RequestParam(defaultValue = "0") Long cursor, CurrentUser user) {
        MomentsDto moments = momentService.getMoments(cursor, user);
        return Response.ok(moments);
    }

}

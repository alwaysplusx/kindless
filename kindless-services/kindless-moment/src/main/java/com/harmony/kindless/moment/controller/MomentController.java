package com.harmony.kindless.moment.controller;

import com.harmony.kindless.apis.domain.moment.Moment;
import com.harmony.kindless.apis.dto.IdDto;
import com.harmony.kindless.apis.dto.MomentDto;
import com.harmony.kindless.apis.dto.MomentsDto;
import com.harmony.kindless.moment.service.MomentService;
import com.harmony.umbrella.context.CurrentUser;
import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuxii
 */
@BundleController
public class MomentController {

    @Autowired
    private MomentService momentService;

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

package com.harmony.kindless.moment.controller;

import com.harmony.kindless.apis.dto.CurrentUser;
import com.harmony.kindless.apis.dto.MomentDto;
import com.harmony.kindless.apis.dto.MomentsDto;
import com.harmony.kindless.moment.service.MomentService;
import com.harmony.umbrella.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxii
 */
@RestController
public class MomentController {

	@Autowired
	private MomentService momentService;

	@PostMapping("/moment/push")
	public Response<String> push(@RequestBody MomentDto moment, CurrentUser user) {
		momentService.push(moment, user);
		return Response.ok("success");
	}

	@GetMapping("/moments")
	public MomentsDto moments(CurrentUser user) {
		return new MomentsDto();
	}

}

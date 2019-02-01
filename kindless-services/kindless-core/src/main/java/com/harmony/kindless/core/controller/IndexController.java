package com.harmony.kindless.core.controller;

import com.harmony.kindless.apis.support.RestUserDetails;
import com.harmony.kindless.core.service.UserTokenService;
import com.harmony.umbrella.web.method.annotation.BundleController;
import com.harmony.umbrella.web.method.annotation.BundleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wuxii
 */
@BundleController
public class IndexController {

	@Autowired
	private UserTokenService userTokenService;

	@BundleView({"password", "authorities"})
	@GetMapping("/security/user_details")
	public RestUserDetails userDetails(String schema, String token) {
		return userTokenService.loadUserByToken(token);
	}

}

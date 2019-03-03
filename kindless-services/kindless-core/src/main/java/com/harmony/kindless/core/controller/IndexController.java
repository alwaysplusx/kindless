package com.harmony.kindless.core.controller;

import com.harmony.kindless.core.userdetails.IdentityUserDetailsService;
import com.harmony.umbrella.security.SecurityToken;
import com.harmony.umbrella.security.userdetails.IdentityUserDetails;
import com.harmony.umbrella.web.Response;
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
    private IdentityUserDetailsService userDetailsService;

    @BundleView({"password", "authorities"})
    @GetMapping("/security/user_details")
    public Response<IdentityUserDetails> userDetails(String schema, String token) {
        IdentityUserDetails userDetails = userDetailsService.loadUserBySecurityToken(new SecurityToken(schema, token));
        return Response.ok(userDetails);
    }

}

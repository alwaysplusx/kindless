package com.harmony.kindless.moment.controller;

import com.harmony.kindless.apis.clients.UserClient;
import com.harmony.kindless.apis.dto.UserDto;
import com.harmony.umbrella.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxii
 */
@RestController
@RequestMapping("test")
public class TestController {

	@Autowired
	private UserClient userClient;

	@GetMapping("/user")
	public Response<UserDto> index(String username) {
		return userClient.getUser(username);
	}

}

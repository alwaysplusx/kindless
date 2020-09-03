package com.kindless.moment.controller;

import com.kindless.client.user.UserClient;
import com.kindless.core.WebResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxii
 */
@RequestMapping("/moment")
@RestController
@RequiredArgsConstructor
public class MomentController {

    private final UserClient userClient;

    @GetMapping("/echo")
    public WebResponse<String> echo(@RequestParam("name") String name) {
        String message = userClient.echo(name);
        return WebResponse.ok(message);
    }

}

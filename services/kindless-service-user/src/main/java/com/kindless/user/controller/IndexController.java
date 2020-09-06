package com.kindless.user.controller;

import com.kindless.core.WebResponse;
import com.kindless.core.auditor.Auditor;
import com.kindless.core.auditor.jwt.AuditorJwt;
import com.kindless.core.dto.IdDto;
import com.kindless.domain.user.User;
import com.kindless.user.dto.AuditorTokenDto;
import com.kindless.user.service.UserService;
import com.kindless.user.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuxin
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class IndexController {

    private final AuditorJwt auditorJwt;

    private final UserService userservice;

    @RequestMapping("/discard")
    public String discard() {
        log.info("discard message");
        return "";
    }

    @PostMapping("/login")
    public WebResponse<Object> login(@RequestParam String username, @RequestParam String password) {
        User user = userservice.findByUsername(username);
        String hashedPassword = user.getPassword();
        if (!PasswordUtils.isMatched(password, hashedPassword)) {
            return WebResponse.failed("wrong password");
        }
        Auditor auditor = Auditor.builder()
                .setUsername(username)
                .setUserId(1L)
                .build();
        String token = auditorJwt.generate(auditor);
        return WebResponse.ok(new AuditorTokenDto(token, auditor));
    }

    @PostMapping("/register")
    public WebResponse<IdDto> register(@RequestBody User user) {
        User savedUser = userservice.register(user);
        return WebResponse.ok(IdDto.of(savedUser));
    }

}

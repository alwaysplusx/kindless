package com.kindless.user.controller;

import com.kindless.core.WebResponse;
import com.kindless.core.auditor.Auditor;
import com.kindless.core.auditor.jwt.AuditorJwt;
import com.kindless.user.dto.AuditorTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxin
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class IndexController {

    private final AuditorJwt auditorJwt;

    @RequestMapping("/discard")
    public String discard() {
        log.info("discard message");
        return "";
    }

    @RequestMapping("/login")
    public WebResponse<Object> login(@RequestParam String username, @RequestParam String password) {
        Auditor auditor = Auditor.builder()
                .setUsername(username)
                .setUserId(1L)
                .build();
        String token = auditorJwt.generate(auditor);
        return WebResponse.ok(new AuditorTokenDto(token, auditor));
    }

}

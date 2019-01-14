package com.harmony.kindless.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

/**
 * @author wuxii
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/kafka")
    public ResponseEntity<String> kafka(@RequestParam(defaultValue = "10") int size) {
        IntStream.range(0, size).forEach(e -> testService.kafka());
        return ResponseEntity.ok("success");
    }

}

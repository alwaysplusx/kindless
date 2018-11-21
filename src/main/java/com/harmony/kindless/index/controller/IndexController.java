package com.harmony.kindless.index.controller;

import com.harmony.umbrella.web.Response;
import com.harmony.umbrella.web.method.annotation.BundleView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxii
 */
@RestController
@RequestMapping
public class IndexController {

    @BundleView("ok")
    @RequestMapping({"", "index"})
    public Response<String> index() {
        return Response.ok("Hello World");
    }

}

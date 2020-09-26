package com.kindless.todo.controller;

import com.kindless.core.dingtalk.DingtalkAction;
import com.kindless.core.dingtalk.DingtalkActionDispatcher;
import com.kindless.core.dingtalk.DingtalkRequest;
import com.kindless.core.dingtalk.DingtalkRequest.DingtalkRequestText;
import com.kindless.core.dingtalk.DingtalkResponse;
import com.kindless.core.dingtalk.action.ActionParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class DingtalkController {

    private final ActionParser actionParser;
    private final DingtalkActionDispatcher dispatcher;

    @RequestMapping("/dingtalk")
    public DingtalkResponse onMessage(@RequestBody(required = false) DingtalkRequest request) {
        try {
            DingtalkAction dingtalkAction = parseToAction(request);
            log.info("接收来自钉钉的消息: {}", dingtalkAction);
            DingtalkResponse response = dispatcher.dispatch(dingtalkAction);
            log.info("返回给钉钉的消息: {}", response);
            return response;
        } catch (Throwable e) {
            return DingtalkResponse.text("error " + e.getMessage());
        }
    }

    private DingtalkAction parseToAction(DingtalkRequest request) {
        DingtalkAction dingtalkAction = new DingtalkAction();
        dingtalkAction.setDingtalkRequest(request);
        DingtalkRequestText text = request.getText();
        if (text != null) {
            String action = text.getContent();
            dingtalkAction.setCommand(actionParser.parse(action));
        }
        return dingtalkAction;
    }

}

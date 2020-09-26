package com.kindless.todo.dingtalk.handler;

import com.kindless.core.dingtalk.DingtalkAction;
import com.kindless.core.dingtalk.DingtalkActionHandler;
import com.kindless.core.dingtalk.DingtalkResponse;
import org.springframework.stereotype.Component;

@Component
public class DeleteTodoActionHandler implements DingtalkActionHandler {

    @Override
    public DingtalkResponse handle(DingtalkAction action) {
        return DingtalkResponse.text("delete");
    }

    @Override
    public boolean canHandle(String action) {
        return "delete".equalsIgnoreCase(action);
    }

}

package com.kindless.core.dingtalk;

import org.springframework.beans.factory.ObjectProvider;

public class DingtalkActionDispatcher {

    private ObjectProvider<DingtalkActionHandler> handlers;

    public DingtalkActionDispatcher(ObjectProvider<DingtalkActionHandler> handlers) {
        this.handlers = handlers;
    }

    public DingtalkResponse dispatch(DingtalkAction action) {
        return getActionHandler(action).handle(action);
    }

    private DingtalkActionHandler getActionHandler(DingtalkAction action) {
        String actionName = action.getAction();
        return handlers
                .stream()
                .filter(e -> e.canHandle(actionName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unable handle action " + actionName));
    }

}

package com.kindless.core.dingtalk;

public interface DingtalkActionHandler {

    DingtalkResponse handle(DingtalkAction action);

    boolean canHandle(String action);

}

package com.kindless.todo.dingtalk;

import com.kindless.dto.user.FindOrCreateUserRequest;
import com.kindless.client.feign.user.UserFeignClient;
import com.kindless.core.dingtalk.DingtalkAction;
import com.kindless.core.dingtalk.DingtalkActionHandler;
import com.kindless.core.dingtalk.DingtalkRequest;
import com.kindless.domain.user.User;
import com.kindless.domain.user.UserAccount;
import org.springframework.util.Assert;

public abstract class AbstractActionHandler implements DingtalkActionHandler {

    protected final String action;

    protected AbstractActionHandler(String action) {
        Assert.notNull(action, "action not allow null");
        this.action = action;
    }

    @Override
    public final boolean canHandle(String action) {
        return this.action.equalsIgnoreCase(action);
    }

    protected abstract UserFeignClient getUserClient();

    protected User getActionUser(DingtalkAction action) {
        DingtalkRequest dingtalkRequest = action.getDingtalkRequest();
        String senderId = dingtalkRequest.getSenderId();
        String nickname = dingtalkRequest.getSenderNick();
        FindOrCreateUserRequest request = new FindOrCreateUserRequest()
                .setUsername(nickname)
                .setNickname(nickname)
                .setAccountType(UserAccount.TYPE_OF_DINGTALK)
                .setAccount(senderId);
        return getUserClient().findOrCreateUserByAccount(request);
    }

}

package com.kindless.core.dingtalk;

import lombok.Data;

import java.util.List;

@Data
public class DingtalkRequest {

    private String msgtype;
    private String msgId;
    private DingtalkRequestText text;
    /**
     * 加密的会话ID
     */
    private String conversationId;
    private String chatbotUserId;

    private String senderId;
    private String senderNick;
    private String senderCorpId;
    private String senderStaffId;

    private boolean isAdmin;
    private long sessionWebhookExpiredTime;
    private long createAt;
    /**
     * 1-单聊、2-群聊
     */
    private String conversationType;
    /**
     * 会话标题（群聊时才有）
     */
    private String conversationTitle;
    private boolean isInAtList;
    private String sessionWebhook;
    private List<DingtalkRequestAt> atUsers;

    @Data
    public static class DingtalkRequestAt {

        private String dingtalkId;
        private String staffId;

    }

    @Data
    public static class DingtalkRequestText {
        private String content;
    }

}

package com.kindless.core.dingtalk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * https://ding-doc.dingtalk.com/doc#/serverapi2/elzz1p/fIh13
 */
@Data
@Accessors(chain = true)
public class DingtalkResponse {

    public static DingtalkResponse empty() {
        return new DingtalkResponse().setMsgtype("empty");
    }

    public static DingtalkResponse text(String text) {
        return new DingtalkResponse()
                .setMsgtype("text")
                .setText(new DingtalkResponseText().setContent(text));
    }

    private String msgtype;
    private DingtalkResponseText text;
    private DingtalkResponseMarkdown markdown;
    private DingtalkResponseActionCard actionCard;
    private DingtalkResponseAt at;

    @Data
    public static class DingtalkResponseText {
        private String content;
    }

    @Data
    public static class DingtalkResponseMarkdown {
        private String title;
        private String text;
    }

    @Data
    public static class DingtalkResponseActionCard {
        private String title;
        private String text;
        private String singleTitle;
        private String singleURL;
        private String hideAvatar;
        /**
         * 0-按钮竖直排列，1-按钮横向排列
         */
        private String btnOrientation;
        @JsonProperty("btns")
        private List<DingtalkResponseActionCardButton> buttons;
    }

    @Data
    public static class DingtalkResponseActionCardButton {
        private String title;
        @JsonProperty("actionURL")
        private String actionUrl;
    }

    @Data
    public static class DingtalkResponseFeedCard {
        private List<DingtalkResponseFeedCardLink> links;
    }

    @Data
    public static class DingtalkResponseFeedCardLink {
        private String title;
        @JsonProperty("messageURL")
        private String messageUrl;
        @JsonProperty("pictureURL")
        private String pictureUrl;
    }

    @Data
    public static class DingtalkResponseAt {

        private List<String> atMobiles;
        private boolean isAtAll;

    }

}

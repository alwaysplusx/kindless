package com.harmony.kindless.messaging.events;

import java.io.Serializable;

/**
 * @author wuxii
 */
public class VisitEvent extends PartitionEvent implements Serializable {

    private Long userId;
    private Long visitorId;
    private Integer partition;

    public Long getUserId() {
        return userId;
    }

    public VisitEvent setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getVisitorId() {
        return visitorId;
    }

    public VisitEvent setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
        return this;
    }

}

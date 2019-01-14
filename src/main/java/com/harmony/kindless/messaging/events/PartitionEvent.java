package com.harmony.kindless.messaging.events;

import java.io.Serializable;

/**
 * @author wuxii
 */
public abstract class PartitionEvent implements Serializable {

    private Integer partition;

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

}

package com.harmony.kindless.data;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.harmony.umbrella.log.Log;
import com.harmony.umbrella.log.Logs;

public class PersistenceContextListener {

    private static final Log log = Logs.getLog(PersistenceContextListener.class);

    @PrePersist
    public void prePersist(Object object) {
        log.debug("apply creator information prePersist {}", object);
        if (object instanceof BaseEntity) {
            EntityUtils.applyCreatorInfoIfNecessary((BaseEntity) object);
        }
    }

    @PreUpdate
    public void preUpdate(Object object) {
        log.debug("apply user information preUpdate {}", object);
        if (object instanceof BaseEntity) {
            EntityUtils.applyUserInfoIfNecessary((BaseEntity) object);
        }
    }

    // @PostPersist
    // public void postPersist(Object object) {
    // }
    //
    // @PreRemove
    // public void preRemove(Object object) {
    // }
    //
    // @PostRemove
    // public void postRemove(Object object) {
    // }
    //
    // @PostUpdate
    // public void postUpdate(Object object) {
    // }

}
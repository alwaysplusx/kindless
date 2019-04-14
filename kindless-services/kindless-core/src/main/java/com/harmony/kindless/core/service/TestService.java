package com.harmony.kindless.core.service;

import com.harmony.kindless.apis.domain.core.User;
import com.harmony.umbrella.data.service.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author wuxii
 */
public interface TestService extends Service<User, Long> {

    Object get1(String username);

    Object get2(String username);

    Object get3(String username);

    Object get4(String username);

    static Object get(EntityManager entityManager, String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(cb.equal(root.get("username"), username));
        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        return typedQuery.getSingleResult();
    }

}

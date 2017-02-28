package com.harmony.kindless.authz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harmony.kindless.authz.domain.Role;

/**
 * @author wuxii@foxmail.com
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}

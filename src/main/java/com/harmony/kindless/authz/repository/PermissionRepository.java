package com.harmony.kindless.authz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harmony.kindless.authz.domain.Permission;

/**
 * @author wuxii@foxmail.com
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

}

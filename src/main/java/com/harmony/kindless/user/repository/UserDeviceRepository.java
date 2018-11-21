package com.harmony.kindless.user.repository;

import org.springframework.stereotype.Repository;

import com.harmony.kindless.apis.domain.user.UserDevice;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii
 */
@Repository
public interface UserDeviceRepository extends QueryableRepository<UserDevice, Long> {

}

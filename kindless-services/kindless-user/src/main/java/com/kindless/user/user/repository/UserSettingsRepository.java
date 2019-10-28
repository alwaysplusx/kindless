package com.kindless.user.user.repository;

import org.springframework.stereotype.Repository;

import com.kindless.user.domain.UserSettings;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii
 */
@Repository
public interface UserSettingsRepository extends QueryableRepository<UserSettings, Long> {

}

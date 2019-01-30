package com.harmony.kindless.core.repository;

import org.springframework.stereotype.Repository;

import com.harmony.kindless.apis.domain.core.UserSettings;
import com.harmony.umbrella.data.repository.QueryableRepository;

/**
 * @author wuxii
 */
@Repository
public interface UserSettingsRepository extends QueryableRepository<UserSettings, Long> {

}

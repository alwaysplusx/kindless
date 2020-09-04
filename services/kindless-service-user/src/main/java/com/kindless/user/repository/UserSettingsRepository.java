package com.kindless.user.repository;

import com.kindless.domain.user.UserSettings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wuxii
 */
@Repository
public interface UserSettingsRepository extends PagingAndSortingRepository<UserSettings, Long> {

}

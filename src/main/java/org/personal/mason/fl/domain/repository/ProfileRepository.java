package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mason on 6/30/14.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
}

package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.PersistentToken;
import org.personal.mason.fl.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by mason on 7/1/14.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, Long> {
    List<PersistentToken> findByUser(User user);
    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);
}

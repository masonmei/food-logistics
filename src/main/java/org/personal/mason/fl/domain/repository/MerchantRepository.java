package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.Merchant;
import org.personal.mason.fl.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by mason on 6/22/14.
 */
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    List<Merchant> findByUser(User user);
}

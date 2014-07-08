package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.ShoppingCar;
import org.personal.mason.fl.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mason on 7/3/14.
 */
public interface ShoppingCarRepository extends JpaRepository<ShoppingCar, Long> {
}

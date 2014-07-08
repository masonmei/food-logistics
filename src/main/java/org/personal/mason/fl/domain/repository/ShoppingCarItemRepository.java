package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.ShoppingCarItem;
import org.personal.mason.fl.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mason on 7/3/14.
 */
public interface ShoppingCarItemRepository extends JpaRepository<ShoppingCarItem, Long> {
}

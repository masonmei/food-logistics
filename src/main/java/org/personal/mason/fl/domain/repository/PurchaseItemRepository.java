package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mason on 6/29/14.
 */
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

}

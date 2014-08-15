package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.Item;
import org.personal.mason.fl.domain.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mason on 6/22/14.
 */
public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {

}

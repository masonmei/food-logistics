package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by mason on 6/22/14.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByName(String name);

    @Query("from Group g where g.name='Unknown'")
    Group getDefaultGroup();
}

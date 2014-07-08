package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by mason on 6/22/14.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByName(String name);

    @Query("from Role r where r.name='USER'")
    Role getDefaultRole();
}

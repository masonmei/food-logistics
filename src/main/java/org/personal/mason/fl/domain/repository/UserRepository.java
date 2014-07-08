package org.personal.mason.fl.domain.repository;

import org.personal.mason.fl.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by mason on 6/22/14.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u From User u where u.email='SYSTEM'")
    User findSystemUser();

    @Query("select u From User u where u.email = :username")
    List<User> loadUsersByUserName(@Param("username") String username);

    @Query("select u from User u join u.roles r where r.name = :rolename")
    List<User> loadUsersByRoleName(@Param("rolename")String rolename);

    @Query("select u From User u Where u.email like :ctx Or u.userNumber like :ctx Or u.profile.phone like :ctx")
    List<User> findByEmailOrUserNumberOrPhone(@Param("ctx") String compare);
}

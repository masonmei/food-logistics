package org.personal.mason.fl.common;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Created by mason on 6/30/14.
 */
public interface RoleManager {

    List<String> findAllRoles();

    List<String> findUsersInRole(String roleName);

    List<String> findGroupsInRole(String roleName);

    void createRole(String roleName);

    void deleteRole(String roleName);

    void renameRole(String oldName, String newName);

    boolean roleExists(String roleName);

    void addUserAuthority(String username, GrantedAuthority authority);

    void removeUserAuthority(String username, GrantedAuthority authority);
}

package org.personal.mason.fl.domain.service;

import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.model.UserRole;

import java.util.List;

/**
 * Created by mason on 6/23/14.
 */
public interface UserRoleService {

    public UserRole getDefaultUserRole();

    public UserRole getDefaultCavalierRole();

    public UserRole getDefaultMerchantRole();

    public UserRole getDefaultAdminRole();

    public List<UserRole> findAll();

    public UserRole findById(Long id);

    public UserRole save(UserRole role);

    public UserRole update(UserRole role);

    public void delete(Long id);
}

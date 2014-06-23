package org.personal.mason.fl.domain.service.impl;

import org.personal.mason.fl.domain.model.UserRole;
import org.personal.mason.fl.domain.repository.UserRoleRepository;
import org.personal.mason.fl.domain.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mason on 6/23/14.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    private Map<UserRole.RoleType, UserRole> defaultRoleMap = new HashMap<>();

    @Autowired
    private UserRoleRepository userRoleRepository;

    private void initRoles() {
        List<UserRole> userRoles = userRoleRepository.findAll();
        for (UserRole role : userRoles) {
            defaultRoleMap.put(UserRole.RoleType.get(role.getRoleType()), role);
        }
    }

    @Override
    public UserRole getDefaultUserRole() {
        if (defaultRoleMap.isEmpty()) {
            initRoles();
        }
        return defaultRoleMap.get(UserRole.RoleType.ROLE_USER);
    }

    @Override
    public UserRole getDefaultCavalierRole() {
        if (defaultRoleMap.isEmpty()) {
            initRoles();
        }
        return defaultRoleMap.get(UserRole.RoleType.ROLE_CAVALIER);
    }

    @Override
    public UserRole getDefaultMerchantRole() {
        if (defaultRoleMap.isEmpty()) {
            initRoles();
        }
        return defaultRoleMap.get(UserRole.RoleType.ROLE_MERCHANT);
    }

    @Override
    public UserRole getDefaultAdminRole() {
        if (defaultRoleMap.isEmpty()) {
            initRoles();
        }
        return defaultRoleMap.get(UserRole.RoleType.ROLE_ADMINISTRATOR);
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole findById(Long id) {
        return userRoleRepository.findOne(id);
    }

    @Override
    public UserRole save(UserRole role) {
        return userRoleRepository.save(role);
    }

    @Override
    public UserRole update(UserRole role) {
        return userRoleRepository.saveAndFlush(role);
    }

    @Override
    public void delete(Long id) {
        userRoleRepository.delete(id);
    }
}

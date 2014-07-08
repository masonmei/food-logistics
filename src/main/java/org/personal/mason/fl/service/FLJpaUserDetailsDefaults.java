package org.personal.mason.fl.service;

import org.personal.mason.fl.common.JpaUserDetailsDefaults;
import org.personal.mason.fl.domain.model.Profile;
import org.personal.mason.fl.domain.model.Role;
import org.personal.mason.fl.domain.model.ShoppingCar;
import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.GroupRepository;
import org.personal.mason.fl.domain.repository.RoleRepository;
import org.personal.mason.fl.domain.repository.UserRepository;
import org.personal.mason.fl.utils.UserNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mason on 6/30/14.
 */
@Service
public class FLJpaUserDetailsDefaults implements JpaUserDetailsDefaults {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialSettings(User model) {
        if(model.getGroups().isEmpty()){
            model.addGroup(groupRepository.getDefaultGroup());
        }
        if(model.getRoles().isEmpty()){
            model.addRole(roleRepository.getDefaultRole());
        }
        model.setUserNumber(UserNumberUtils.createNumber(Role.RoleType.ROLE_USER.getKey(), userRepository.count()));
        if(model.getProfile() == null){
            model.setProfile(new Profile());
        }
        if(model.getShoppingCar() == null){
            model.setShoppingCar(new ShoppingCar());
        }
    }
}

package org.personal.mason.fl.domain.service.impl;

import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.UserRepository;
import org.personal.mason.fl.domain.service.UserRoleService;
import org.personal.mason.fl.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by mason on 6/22/14.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public Long add(User user) {
        if (user == null) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setUserRole(userRoleService.getDefaultUserRole());
        return userRepository.save(user).getId();
    }
}

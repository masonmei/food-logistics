package org.personal.mason.fl.domain.service;

import org.personal.mason.fl.domain.model.User;

/**
 * Created by mason on 6/22/14.
 */
public interface UserService {
    User findById(Long id);

    Long add(User user);
}

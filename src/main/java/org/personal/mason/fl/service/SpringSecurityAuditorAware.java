package org.personal.mason.fl.service;

import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by mason on 6/30/14.
 */
public class SpringSecurityAuditorAware implements AuditorAware<User>, ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(SpringSecurityAuditorAware.class);

    @Autowired
    private UserRepository userRepository;
    private User systemUser;

    @Override
    public User getCurrentAuditor() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal;
        if (authentication == null || !authentication.isAuthenticated()) {
            principal = systemUser;
        } else {
            principal = (User) authentication.getPrincipal();
        }
        logger.info(String.format("Current auditor is >>> %s", principal));
        return principal;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (this.systemUser == null) {
            logger.info("%s >>> loading system user");
            systemUser = this.userRepository.findSystemUser();
        }
    }
}

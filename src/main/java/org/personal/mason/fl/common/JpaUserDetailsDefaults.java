package org.personal.mason.fl.common;

import org.personal.mason.fl.domain.model.User;

/**
 * Created by mason on 6/30/14.
 */
public interface JpaUserDetailsDefaults {

    void initialSettings(User model);

    public class NullJpaUserDetailsDefaults implements JpaUserDetailsDefaults{

        @Override
        public void initialSettings(User model) {

        }
    }
}

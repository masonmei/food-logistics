package org.personal.mason.fl.web.controller;


import org.personal.mason.fl.domain.model.Profile;
import org.personal.mason.fl.domain.model.Role;
import org.personal.mason.fl.domain.model.ShoppingCar;
import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.ContactRepository;
import org.personal.mason.fl.domain.repository.ProfileRepository;
import org.personal.mason.fl.domain.repository.UserRepository;
import org.personal.mason.fl.service.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by mason on 6/23/14.
 */
public abstract class AbstractController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ContactRepository contactRepository;

    public <T> void mergeModel(T to, T from) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(from.getClass());

            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor pd : pds) {
                Method writeMethod = pd.getWriteMethod();
                Method readMethod = pd.getReadMethod();

                if (readMethod == null || writeMethod == null) {
                    continue;
                }

                Object value = readMethod.invoke(from);
                if (value == null) {
                    continue;
                }
                if(value instanceof Collection){
                    Collection<?> colVal = (Collection<?>) value;
                    if(colVal.isEmpty()){
                        continue;
                    }
                }

                writeMethod.invoke(to, value);
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public <T> void updateModel(T to, T from) {
        BeanUtils.copyProperties(from, to, "id");
    }


    //~===========================================================================================

    protected User getLoginUser() {
        String username = SecurityUtils.getCurrentLogin();
        if (username != null) {
            try {
                return userRepository.loadUsersByUserName(username).get(0);
            } catch (Exception e) {

            }
        }
        return null;
    }

    protected ShoppingCar getMyShoppingCar() {
        User user = getLoginUser();
        if (user != null) {
            return user.getShoppingCar();
        } else {
            return null;
        }
    }

    protected Profile getCurrentProfile() {
        User user = getLoginUser();
        return user.getProfile();
    }

    protected boolean hasRole(String rolename) {
        User loginUser = getLoginUser();
        if(loginUser == null){
            return false;
        }
        Set<Role> roles = loginUser.getRoles();
        for(Role role : roles){
            if(role.getName().equals(rolename)){
                return true;
            }
        }
        return false;
    }

    protected Date toStartOfDay(Date date){
        if(date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
        return null;
    }

    protected Date toEndOfDay(Date date){
        if(date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            return calendar.getTime();
        }
        return null;
    }

    protected Collection<String> splitToArray(String status){
        if(status == null){
            status = "";
        }
        return Arrays.asList(status.split("[\\s]*,[\\s]*"));
    }

}

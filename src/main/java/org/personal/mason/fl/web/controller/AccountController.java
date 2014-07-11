package org.personal.mason.fl.web.controller;

import org.personal.mason.fl.common.JPAUserDetails;
import org.personal.mason.fl.common.JPAUserDetailsManager;
import org.personal.mason.fl.domain.model.*;
import org.personal.mason.fl.domain.repository.ContactRepository;
import org.personal.mason.fl.domain.repository.ProfileRepository;
import org.personal.mason.fl.domain.repository.UserRepository;
import org.personal.mason.fl.utils.convert.*;
import org.personal.mason.fl.web.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mason on 6/22/14.
 */
@Controller
public class AccountController extends AbstractController {

    @Autowired
    private JPAUserDetailsManager userDetailsManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping(value = {"account/register"}, method = RequestMethod.POST)
    public ResponseEntity<?> registerAccount(@RequestBody PoUser poUser) {
        try {
            User user = UserConverter.toModel(poUser, ConvertType.FULL);
            UserDetails userDetails = new JPAUserDetails(user);
            userDetailsManager.createUser(userDetails);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = {"account/exist"}, method = RequestMethod.GET)
    public ResponseEntity<?> checkExist(@RequestParam String username) {
        try {
            boolean result = userDetailsManager.userExists(username);

            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Protected
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"account"}, method = RequestMethod.GET)
    public ResponseEntity<PoUser> myInfo(){
        User loginUser = getLoginUser();
        PoUser poUser = UserConverter.fromModel(loginUser);
        return new ResponseEntity<>(poUser, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"account"}, method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody PoUser poUser, UriComponentsBuilder builder) {
        try {
            User user = UserConverter.toModel(poUser, ConvertType.FLAT);
            User model = getLoginUser();
            updateModel(model, user);

            UserDetails userDetails = new JPAUserDetails(model);
            userDetailsManager.updateUser(userDetails);

            HttpHeaders headers = new HttpHeaders();
            if (user != null) {
                headers.setLocation(builder.path("account/" + user.getId()).build().toUri());
                return new ResponseEntity<>(headers, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = {"account/password"}, method = RequestMethod.POST)
    public ResponseEntity<?> updatePasswod(@RequestBody PoPassword password){
        try{
            userDetailsManager.changePassword(password.getOldPassword(), password.getNewPassword());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = {"account/{username}"}
    )
    public ResponseEntity<?> delete(@PathVariable String username) {
        try {
            userDetailsManager.deleteUser(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = {"account/profile"},
            method = RequestMethod.GET
    )
    public ResponseEntity<PoProfile> findUserProfile(){
        User user = getLoginUser();
        try{
            PoProfile profile = ProfileConverter.fromModel(user.getProfile());
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(
            value = {"account/profile"},
            method = RequestMethod.PUT
    )
    public ResponseEntity<?> updateProfile(@RequestBody PoProfile profile, UriComponentsBuilder builder){
        try{
            Profile model = profileRepository.saveAndFlush(ProfileConverter.toModel(profile));

            HttpHeaders headers = new HttpHeaders();
            if (model != null) {
                headers.setLocation(builder.path("account/profile").build().toUri());
                return new ResponseEntity<>(headers, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "account/role", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRole(){
        try {
            User user = getLoginUser();
            List<PoRole> roles = new ArrayList<>();
            for(Role model : user.getRoles()){
                PoRole role = RoleConverter.fromModel(model, ConvertType.FLAT);
                roles.add(role);
            }

            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "account/group", method = RequestMethod.GET)
    public ResponseEntity<?> getAllGroup(){
        try {
            User user = getLoginUser();
            List<PoGroup> groups = new ArrayList<>();
            for(Group model : user.getGroups()){
                PoGroup group = GroupConverter.fromModel(model, ConvertType.FLAT);
                groups.add(group);
            }
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    // ~  Contact ==================================================================================

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET,
            value = {"account/profile/contact"}
    )
    public ResponseEntity<List<PoContact>> findAllContacts() {
        User user = getLoginUser();
        List<PoContact> poContacts = ContactConverter.fromModel(new ArrayList<Contact>(user.getProfile().getContacts()));
        if (poContacts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(poContacts, HttpStatus.OK);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET,
            value = {"account/profile/contact/{id}"}
    )
    public ResponseEntity<PoContact> findById(@PathVariable Long id) {
        PoContact poContact = ContactConverter.fromModel(contactRepository.findOne(id));
        if (poContact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(poContact, HttpStatus.OK);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = {RequestMethod.POST},
            value = {"account/profile/contact"}
    )
    public ResponseEntity<PoContact> createContact(@RequestBody PoContact poContact, UriComponentsBuilder builder) {
        Profile profile = getCurrentProfile();
        Contact contact = ContactConverter.toModel(poContact);
        profile.addContact(contact);
        contactRepository.saveAndFlush(contact);
        profile = profileRepository.saveAndFlush(profile);

        HttpHeaders headers = new HttpHeaders();
        if (profile != null) {
            headers.setLocation(builder.path("account/profile/contact/" + profile.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = {RequestMethod.PUT},
            value = {"account/profile/contact/{id}"}
    )
    public ResponseEntity<PoContact> updateContact(@PathVariable Long id, @RequestBody PoContact poContact, UriComponentsBuilder builder) {
        Contact contact = ContactConverter.toModel(poContact);
        Contact model = contactRepository.findOne(id);
        updateModel(model, contact);
        Contact result = contactRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("contact/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = {RequestMethod.PATCH},
            value = {"account/profile/contact/{id}"}
    )
    public ResponseEntity<PoContact> partialUpdateContact(@PathVariable Long id, @RequestBody PoContact poContact, UriComponentsBuilder builder) {
        Contact contact = ContactConverter.toModel(poContact, ConvertType.FLAT);
        Contact model = contactRepository.findOne(id);

        mergeModel(model, contact);
        Contact result = contactRepository.saveAndFlush(model);

        HttpHeaders headers = new HttpHeaders();
        if (result != null) {
            headers.setLocation(builder.path("account/profile/contact/" + result.getId()).build().toUri());
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.DELETE,
            value = {"account/profile/contact/{id}"}
    )
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            contactRepository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


}

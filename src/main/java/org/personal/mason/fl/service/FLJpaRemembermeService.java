package org.personal.mason.fl.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.personal.mason.fl.common.JPAUserDetailsService;
import org.personal.mason.fl.domain.model.PersistentToken;
import org.personal.mason.fl.domain.model.User;
import org.personal.mason.fl.domain.repository.PersistentTokenRepository;
import org.personal.mason.fl.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Service;

/**
 * Created by mason on 7/1/14.
 */
@Service
public class FLJpaRememberMeService extends AbstractRememberMeServices {
    private static final String DEFAULT = "remember_me_key";
    private static final String KEY = "fl.security.rememberme.key";
    private final Logger logger = LoggerFactory.getLogger(FLJpaRememberMeService.class);

    private static final int TOKEN_VALIDITY_DAYS = 31;
    private static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * TOKEN_VALIDITY_DAYS;
    private static final int DEFAULT_TOKEN_LENGTH = 16;

    private SecureRandom random;

    @Autowired
    public FLJpaRememberMeService(Environment env, JPAUserDetailsService userDetailsService){
        super(env.getProperty(KEY, DEFAULT), userDetailsService);
        random = new SecureRandom();
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;


    @Override
    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        String login = successfulAuthentication.getName();

        User user = userRepository.loadUsersByUserName(login).get(0);
        PersistentToken token = new PersistentToken();
        token.setUser(user);
        token.setTokenValue(generateTokenData());
        token.setTokenDate(Calendar.getInstance().getTime());
        token.setIpAddress(request.getRemoteAddr());
        token.setUserAgent(request.getHeader("User-Agent"));
        try{
            persistentTokenRepository.save(token);
            addCookie(token, request, response);
        } catch (DataAccessException e){
            logger.error("Failed to save persistent token ", e);
        }
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) throws RememberMeAuthenticationException, UsernameNotFoundException {

        PersistentToken token = getPersistentToken(cookieTokens);
        String login = token.getUser().getEmail();

        logger.debug("Refreshing persistent login token for user '{}', series '{}'", login, token.getId());
        token.setTokenDate(Calendar.getInstance().getTime());
        token.setTokenValue(generateTokenData());
        token.setIpAddress(request.getRemoteAddr());
        token.setUserAgent(request.getHeader("User-Agent"));

        try {
            persistentTokenRepository.saveAndFlush(token);
            addCookie(token, request, response);
        } catch (DataAccessException e) {
            logger.error("Failed to update token: ", e);
            throw new RememberMeAuthenticationException("Autologin failed due to data access problem", e);
        }
        return getUserDetailsService().loadUserByUsername(login);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String rememberMeCookie = extractRememberMeCookie(request);
        if (rememberMeCookie != null && rememberMeCookie.length() != 0) {
            try {
                String[] cookieTokens = decodeCookie(rememberMeCookie);
                PersistentToken token = getPersistentToken(cookieTokens);
                persistentTokenRepository.delete(token);
            } catch (InvalidCookieException ice) {
                logger.info("Invalid cookie, no persistent token could be deleted");
            } catch (RememberMeAuthenticationException rmae) {
                logger.debug("No persistent token found, so no token could be deleted");
            }
        }
        super.logout(request, response, authentication);
    }

    private PersistentToken getPersistentToken(String[] cookieTokens) {
        if (cookieTokens.length != 2) {
            throw new InvalidCookieException("Cookie token did not contain " + 2 +
                    " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
        }

        final Long presentedId = new Long(cookieTokens[0]);
        final String presentedToken = cookieTokens[1];

        PersistentToken token = persistentTokenRepository.findOne(presentedId);

        if (token == null) {
            // No series match, so we can't authenticate using this cookie
            throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedId);
        }

        // We have a match for this user/series combination
        logger.info("presentedToken={} / tokenValue={}", presentedToken, token.getTokenValue());
        if (!presentedToken.equals(token.getTokenValue())) {
            // Token doesn't match series value. Delete this session and throw an exception.
            persistentTokenRepository.delete(token);
            throw new CookieTheftException("Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack.");
        }

        Date date = token.getTokenDate();
        if (DateUtils.addDays(date, TOKEN_VALIDITY_DAYS).before(Calendar.getInstance().getTime())){
            persistentTokenRepository.delete(token);
            throw new RememberMeAuthenticationException("Remember-me login has expired");
        }
        return token;
    }
    
    private void addCookie(PersistentToken token, HttpServletRequest request, HttpServletResponse response) {
        setCookie(
                new String[]{token.getId().toString(), token.getTokenValue()},
                TOKEN_VALIDITY_SECONDS, request, response);
    }

    private String generateTokenData() {
        byte[] newToken = new byte[DEFAULT_TOKEN_LENGTH];
        random.nextBytes(newToken);
        return new String(Base64.encode(newToken));
    }

}

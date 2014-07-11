package org.personal.mason.fl.service;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8448535416518984499L;

	private final Object principal = null;
	private Object details;
	
	public JWTAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		super.setAuthenticated(true);
		
//		this.principal = par;
//		this.set
	}

	
	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

}

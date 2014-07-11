package org.personal.mason.fl.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the user_forget database table.
 */
@Entity
@Table(name = "fl_forget")
public class Forget extends Auditing<User, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String email;
	private String token;
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "request_time")
	private Date requestTime;

	public Forget() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
}
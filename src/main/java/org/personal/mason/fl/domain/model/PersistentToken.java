package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mason on 7/1/14.
 */
@Entity
@Table(name = "fl_persistent_token")
public class PersistentToken extends AbstractAuditable<User, Long> implements Serializable{
	private static final long serialVersionUID = 5809588224228237411L;

	@Column(name = "token_value")
    private String tokenValue;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="token_date")
    private Date tokenDate;

    @Column(name = "ip_address", length = 39)
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(Date tokenDate) {
        this.tokenDate = tokenDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

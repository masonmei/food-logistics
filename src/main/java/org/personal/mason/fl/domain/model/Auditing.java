package org.personal.mason.fl.domain.model;

import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by mason on 7/8/14.
 */

//@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Auditing<U, PK extends Serializable> extends AbstractPersistable<PK> {

	private static final long serialVersionUID = 3174991428322641479L;

	@CreatedBy
	@Column(name = "created_by_id")
	private U createdBy;

	@CreatedDate
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@LastModifiedBy
	@Column(name = "last_modified_by_id")
	private U lastModifiedBy;

	@LastModifiedDate
	@Column(name = "last_modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@Version
	@Column(name = "version")
	private Integer version;

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}

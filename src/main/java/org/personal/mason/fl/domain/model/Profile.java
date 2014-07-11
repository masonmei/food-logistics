package org.personal.mason.fl.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by mason on 6/30/14.
 */

@Entity
@Table(name = "fl_profile")
public class Profile extends Auditing<User, Long> implements Serializable {

	private static final long serialVersionUID = 6101794280094991864L;

	@Column(name = "display_name", length = 48)
	private String displayName;

	@Column(length = 30)
	private String phone;

	@Column(name = "vip")
	private Boolean vip = Boolean.FALSE;

	@OneToMany(mappedBy = "profile")
	private Set<Contact> contacts = new HashSet<>();

	public Profile() {
	}

	public Profile(Long id) {
		setId(id);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	// bi-directional many-to-one association to UserContact
	public Set<Contact> getContacts() {
		return this.contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public Contact addContact(Contact contact) {
		getContacts().add(contact);
		contact.setProfile(this);
		return contact;
	}

	public Contact removeContact(Contact contact) {
		getContacts().remove(contact);
		contact.setProfile(null);

		return contact;
	}
}

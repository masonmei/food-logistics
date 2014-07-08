package org.personal.mason.fl.domain.model;

import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the user_role database table.
 */
@Entity
@Table(name = "fl_role")
public class Role extends AbstractAuditable<User, Long> implements Serializable {

	public enum RoleType {
		ROLE_USER("U"), ROLE_CAVALIER("C"), ROLE_MERCHANT("M"), ROLE_ADMINISTRATOR(
				"A");

		private String key;

		private RoleType(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public static RoleType get(String key) {
			for (RoleType type : RoleType.values()) {
				if (type.getKey().equals(key)) {
					return type;
				}
			}
			return ROLE_USER;
		}
	}

	/**
     *
     */
	private static final long serialVersionUID = 2717028700261627699L;

	@Column(name = "name", length = 64, unique = true)
	private String name;

	@Column(name = "enable")
	private Boolean enable = Boolean.TRUE;

	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();

	@ManyToMany(mappedBy = "roles")
	private Set<Group> groups = new HashSet<>();

	public Role() {
	}

	public Role(Long id) {
		setId(id);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// bi-directional many-to-one association to PoUser
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
}
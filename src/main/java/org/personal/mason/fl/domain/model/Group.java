package org.personal.mason.fl.domain.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * The persistent class for the user_group database table.
 */
@Entity
@Table(name = "fl_group")
public class Group extends Auditing<User, Long> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "name", nullable = false, length = 100, unique = true)
	private String name;

	@ManyToMany(targetEntity = User.class)
	@JoinTable(name = "fl_group_member", joinColumns = { @JoinColumn(name = "group_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private Set<User> users = new HashSet<>();

	@ManyToMany(targetEntity = Role.class)
	@JoinTable(name = "fl_group_role", joinColumns = { @JoinColumn(name = "group_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<>();

	@Column(name = "enable")
	private Boolean enable = Boolean.TRUE;

	public Group() {
	}

	public Group(Long id) {
		setId(id);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		if (user == null) {
			return null;
		}

		getUsers().add(user);
		user.getGroups().add(this);

		return user;
	}

	public User removeUser(User user) {
		if (user == null) {
			return null;
		}

		getUsers().remove(user);
		user.getGroups().remove(this);

		return user;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Role addRole(Role role) {
		if (role == null) {
			return null;
		}

		getRoles().add(role);
		role.getGroups().add(this);

		return role;
	}

	public Role removeRole(Role role) {
		if (role == null) {
			return null;
		}

		getRoles().remove(role);
		role.getGroups().remove(this);

		return role;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
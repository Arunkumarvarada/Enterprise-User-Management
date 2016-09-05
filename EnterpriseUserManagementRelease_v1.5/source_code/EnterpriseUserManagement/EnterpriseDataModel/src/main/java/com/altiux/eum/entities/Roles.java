/**
 * 
 */
package com.altiux.eum.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Roles")
public class Roles {

	@Id
	@GeneratedValue(generator = "role_id_generator")
	@GenericGenerator(name = "role_id_generator", strategy = "com.altiux.eum.entities.idgenerator.RoleIdGenerator")
	@Column(name = "roleId")
	private String roleId;

	@Column(name = "roleName")
	private String roleName;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "RoleOperations", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = {
			@JoinColumn(name = "operationId") })
	private List<Operations> operations = new ArrayList<Operations>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private List<User> User = new ArrayList<User>();

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Operations> getOperations() {
		return operations;
	}

	public void setOperations(List<Operations> operations) {
		this.operations.addAll(operations);
	}

	public List<User> getUser() {
		return User;
	}

	public void setUser(List<User> user) {
		User = user;
	}

//	@Override
//	public String toString() {
//		return "Roles [roleId=" + roleId + ", roleName=" + roleName + ", operations=" + operations + ", User=" + User
//				+ "]";
//	}

	public Roles(String roleId, String roleName, List<Operations> operations,
			List<com.altiux.eum.entities.User> user) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.operations = operations;
		User = user;
	}

	public Roles() {
		super();
	}

}

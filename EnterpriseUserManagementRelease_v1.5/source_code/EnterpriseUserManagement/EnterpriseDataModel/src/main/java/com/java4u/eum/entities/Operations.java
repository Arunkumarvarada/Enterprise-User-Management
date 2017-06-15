package com.java4u.eum.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Arunkumar.V
 *
 */
@Entity
@Table(name = "Operations")
public class Operations {

	@Id
	@GeneratedValue(generator = "operation_id_generator")
	@GenericGenerator(name = "operation_id_generator", strategy = "com.altiux.eum.entities.idgenerator.OperationIdGenerator")
	@Column(name = "operationId")
	private String operationId;

	@Column(name = "moduleOperationName")
	private String moduleOperationName;

	@Column(name = "readPermission")
	private Boolean readPermission;

	@Column(name = "executePermission")
	private Boolean executePermission;

	@Column(name = "writePermission")
	private Boolean writePermission;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "operations")
	private List<Roles> roles = new ArrayList<Roles>();

	/*@ManyToMany(fetch = FetchType.LAZY, mappedBy = "permittedOperations")
	private List<User> users = new ArrayList<User>();*/

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getModuleOperationName() {
		return moduleOperationName;
	}

	public void setModuleOperationName(String moduleOperationName) {
		this.moduleOperationName = moduleOperationName;
	}

	public Boolean getReadPermission() {
		return readPermission;
	}

	public void setReadPermission(Boolean readPermission) {
		this.readPermission = readPermission;
	}

	public Boolean getExecutePermission() {
		return executePermission;
	}

	public void setExecutePermission(Boolean executePermission) {
		this.executePermission = executePermission;
	}

	public Boolean getWritePermission() {
		return writePermission;
	}

	public void setWritePermission(Boolean writePermission) {
		this.writePermission = writePermission;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	

	public Operations(String operationId, String moduleOperationName, Boolean readPermission, Boolean executePermission,
			Boolean writePermission, List<Roles> roles) {
		super();
		this.operationId = operationId;
		this.moduleOperationName = moduleOperationName;
		this.readPermission = readPermission;
		this.executePermission = executePermission;
		this.writePermission = writePermission;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Operations [operationId=" + operationId + ", moduleOperationName=" + moduleOperationName
				+ ", readPermission=" + readPermission + ", executePermission=" + executePermission
				+ ", writePermission=" + writePermission + ", roles=" + roles + "]";
	}

	public Operations() {
		super();
	}
	
	public void addRole(Roles role){
		roles.add(role);
		
	}

}

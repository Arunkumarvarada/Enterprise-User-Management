package com.java4u.eum.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "EnterpriseSystemUser")
public class EnterpriseSystemUser extends User {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eSystemId")
	private EnterpriseSystem eSystem;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.PERSIST }, mappedBy = "users")
	private List<EnterpriseSite> enterpriseSites = new ArrayList<EnterpriseSite>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "enterpriseSystemUser", cascade = CascadeType.REMOVE)
	private List<UserRequest> userRequests = new ArrayList<UserRequest>();

	public void add(EnterpriseSite parkingSpace) {
		this.enterpriseSites.add(parkingSpace);
	}

	public void addUserRequest(UserRequest userRequest) {
		this.userRequests.add(userRequest);
	}

	public void addEnterpriseSite(EnterpriseSite parkingSite) {
		this.enterpriseSites.add(parkingSite);
	}

	public void removeEnterpriseSite(EnterpriseSite parkingSite) {
		this.enterpriseSites.add(parkingSite);
	}

	public EnterpriseSystem geteSystem() {
		return eSystem;
	}

	public void seteSystem(EnterpriseSystem eSystem) {
		this.eSystem = eSystem;
	}

	public List<EnterpriseSite> getEnterpriseSites() {
		return enterpriseSites;
	}

	public void setEnterpriseSites(List<EnterpriseSite> enterpriseSites) {
		this.enterpriseSites = enterpriseSites;
	}

	public List<UserRequest> getUserRequests() {
		return userRequests;
	}

	public void setUserRequests(List<UserRequest> userRequests) {
		this.userRequests = userRequests;
	}

	public EnterpriseSystemUser(EnterpriseSystem eSystem, List<EnterpriseSite> enterpriseSites,
			List<UserRequest> userRequests) {
		super();
		this.eSystem = eSystem;
		this.enterpriseSites = enterpriseSites;
		this.userRequests = userRequests;
	}

	public EnterpriseSystemUser() {
		super();
	}

	public EnterpriseSystemUser(String userId, String userName, String password, String familyName, String middleName,
			String givenName, String displayName, String nickName, String honorificPrifix, String role,
			boolean isActive, Timestamp created, Timestamp lastModifiedTime, String formattedName, List<Email> emails,
			List<PhoneNumber> phoneNumbers, List<Photo> pics, Roles roles, List<String> permittedOperations) {
		super(userId, userName, password, familyName, middleName, givenName, displayName, nickName, honorificPrifix,
				role, isActive, created, lastModifiedTime, formattedName, emails, phoneNumbers, pics, roles,
				permittedOperations);

	}

}

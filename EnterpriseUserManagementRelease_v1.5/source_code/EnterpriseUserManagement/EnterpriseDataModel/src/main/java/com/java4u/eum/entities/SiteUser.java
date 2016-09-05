package com.altiux.eum.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SiteUser")
public class SiteUser {
	@Id
	@GeneratedValue(generator = "ses_user_id_generator")
	@GenericGenerator(name = "ses_user_id_generator", strategy = "com.altiux.eum.entities.idgenerator.SiteUserIdGenerator")
	@Column(name = "siteUserId")
	private String siteUserId;

	@Column(unique = true, name = "userName")
	@Nonnull
	private String userName;
	
	@Column(name = "password")
	@Nonnull
	private String password;

	@Column(name = "familyName")
	@Nonnull
	private String familyName;

	@Column(name = "middleName")
	private String middleName;

	@Column(name = "givenName")
	@Nonnull
	private String givenName;

	@Column(name = "displayName")
	private String displayName;

	@Column(name = "nickName")
	private String nickName;

	@Column(name = "honorificPrifix")
	private String honorificPrifix;

	@Column(name = "role")
	@Nonnull
	private String role;

	@Column(name = "isActive")
	@Nonnull
	private boolean isActive;

	@Column(name = "created")
	@Nonnull
	private Timestamp created;

	@Column(name = "lastModifiedTime")
	@Nonnull
	private Timestamp lastModifiedTime;

	@Column(name = "formattedName")
	@Nonnull
	private String formattedName;

	@ElementCollection
	@CollectionTable(name = "UserEmails", joinColumns = @JoinColumn(name = "siteUserId") )
	private List<Email> emails = new ArrayList<Email>();

	@ElementCollection
	@CollectionTable(name = "UserPhoneNumbers", joinColumns = @JoinColumn(name = "siteUserId") )
	private List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();

	@ElementCollection
	@CollectionTable(name = "UserPhotos", joinColumns = @JoinColumn(name = "siteUserId") )
	private List<Photo> pics = new ArrayList<Photo>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId")
	private Roles roles;

	@ElementCollection
	@CollectionTable(name = "SiteUserPermittedOperations", joinColumns = @JoinColumn(name = "siteUserId") )
	private List<String> permittedOperations = new ArrayList<String>();

	@ManyToOne
	@JoinColumn(name="eSiteId")
	private EnterpriseSite enterpriseSite;
	
	public EnterpriseSite getEnterpriseSite() {
		return enterpriseSite;
	}

	public void setEnterpriseSite(EnterpriseSite enterpriseSite) {
		this.enterpriseSite = enterpriseSite;
	}

	public SiteUser() {

	}

	public SiteUser(String siteUserId, String userName, String password, String familyName, String middleName, String givenName,
			String displayName, String nickName, String honorificPrifix, String role, boolean isActive,
			Timestamp created, Timestamp lastModifiedTime, String formattedName, List<Email> emails,
			List<PhoneNumber> phoneNumbers, List<Photo> pics, Roles roles, List<String> permittedOperations
			) {
		super();
		this.siteUserId = siteUserId;
		this.userName = userName;
		this.password = password;
		this.familyName = familyName;
		this.middleName = middleName;
		this.givenName = givenName;
		this.displayName = displayName;
		this.nickName = nickName;
		this.honorificPrifix = honorificPrifix;
		this.role = role;
		this.isActive = isActive;
		this.created = created;
		this.lastModifiedTime = lastModifiedTime;
		this.formattedName = formattedName;
		this.emails = emails;
		this.phoneNumbers = phoneNumbers;
		this.pics = pics;
		this.roles = roles;
		this.permittedOperations = permittedOperations;
		
	}

	public String getSiteUserId() {
		return siteUserId;
	}

	public void setSiteUserId(String siteUserId) {
		this.siteUserId = siteUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHonorificPrifix() {
		return honorificPrifix;
	}

	public void setHonorificPrifix(String honorificPrifix) {
		this.honorificPrifix = honorificPrifix;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Timestamp lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getFormattedName() {
		return formattedName;
	}

	public void setFormattedName(String formattedName) {
		this.formattedName = formattedName;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<Photo> getPics() {
		return pics;
	}

	public void setPics(List<Photo> pics) {
		this.pics = pics;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public List<String> getPermittedOperations() {
		return permittedOperations;
	}

	public void setPermittedOperations(List<String> permittedOperations) {
		this.permittedOperations = permittedOperations;
	}
	
	public void addPermittedOperations(String operationId) {
		this.permittedOperations.add(operationId);
	}


}

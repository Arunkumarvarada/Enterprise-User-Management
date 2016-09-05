package com.altiux.eum.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "EnterpriseSite")
@NamedQueries({
		@NamedQuery(name = "findEnterpriseSiteById", query = "from EnterpriseSite ps where ps.eSiteId=:eSiteId"),
		@NamedQuery(name = "getAllSiteIds", query = "Select eSiteId from EnterpriseSite ps where ps.active=true") })
public class EnterpriseSite {
	// Primary Key.
	@Id
	//@GeneratedValue(generator = "eSite_id_generator")
	//@GenericGenerator(name = "eSite_id_generator", strategy = "com.altiux.eum.entities.idgenerator.EnterpriseSiteIdGenerator")
	@Column(name = "eSiteId")
	private String eSiteId;

	@Column(name = "eSiteName")
	private String eSiteName;

	@Embedded
	private Specifics specifics;

	@Column(name = "created")
	private Timestamp created;

	@Column(name = "lastmodifiedTime")
	private Timestamp lastmodifiedTime;

	@Column(name = "numberOfFloors")
	private int numberOfFloors;

	@Column(name = "serviceProviderId")
	private String serviceProviderId;

	@Column(name = "metering")
	private String metering;

	@Column(name = "orientation")
	private String orientation;

	@Embedded
	private EnterpriseSiteContext enterpriseSiteContext;

	@Column(name = "active")
	private Boolean active = true;

	@Column(name = "published")
	private Boolean published = false;

	@Column(name = "cimNotified")
	private Boolean cimNotified = false;

	@Column(name = "cimNotifiedTime")
	private Timestamp cimNotifiedTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eSystemId")
	private EnterpriseSystem eSystem;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { 
        CascadeType.DETACH,
        CascadeType.MERGE,
        CascadeType.REFRESH,
        CascadeType.PERSIST
})
	@JoinTable(name = "EnterpriseSiteUserMapping", joinColumns = {
			@JoinColumn(name = "eSiteId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
	private List<EnterpriseSystemUser> users = new ArrayList<EnterpriseSystemUser>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "enterpriseSite", cascade = CascadeType.REMOVE)
	private List<Gateway> controllers = new ArrayList<Gateway>();

	@ElementCollection
	@CollectionTable(name = "EnterpriseSiteEmails", joinColumns = @JoinColumn(name = "eSiteId"))
	private List<Email> emails = new ArrayList<Email>();

	@ElementCollection
	@CollectionTable(name = "EnterpriseSitePhoneNumbers", joinColumns = @JoinColumn(name = "eSiteId"))
	private List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();

	@ElementCollection
	@CollectionTable(name = "ViolationConfiguration", joinColumns = @JoinColumn(name = "eSiteId"))
	private List<ViolationConfiguration> violationconfiguration = new ArrayList<ViolationConfiguration>();

	@ElementCollection
	@CollectionTable(name = "EnterpriseSitePhotos", joinColumns = @JoinColumn(name = "eSiteId"))
	private List<Photo> pics = new ArrayList<Photo>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "enterpriseSite", cascade = CascadeType.REMOVE)
	private List<UserRequest> userRequests = new ArrayList<UserRequest>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "enterpriseSite", cascade = CascadeType.REMOVE)
	private List<Device> devices = new ArrayList<Device>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "enterpriseSite", cascade = CascadeType.REMOVE)
	private List<SiteUser> siteUser = new ArrayList<SiteUser>();
	
	public Specifics getSpecifics() {
		return specifics;
	}

	public void setSpecifics(Specifics specifics) {
		this.specifics = specifics;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getLastmodifiedTime() {
		return lastmodifiedTime;
	}

	public void setLastmodifiedTime(Timestamp lastmodifiedTime) {
		this.lastmodifiedTime = lastmodifiedTime;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public List<Gateway> getControllers() {
		return controllers;
	}

	public void setControllers(List<Gateway> controllers) {
		this.controllers = controllers;
	}

	public String getServiceProviderId() {
		return serviceProviderId;
	}

	public void setServiceProviderId(String serviceProviderId) {
		this.serviceProviderId = serviceProviderId;
	}

	// public ParkingSpaceUser getAdmin() {
	// return admin;
	// }
	//
	// public void setAdmin(ParkingSpaceUser admin) {
	// this.admin = admin;
	// }

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

	public String getMetering() {
		return metering;
	}

	public void setMetering(String metering) {
		this.metering = metering;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	public void addController(Gateway controller) {
		controllers.add(controller);
	}

	public void deleteController(Gateway controller) {
		controllers.remove(controller);
	}

	public List<ViolationConfiguration> getViolationconfiguration() {
		return violationconfiguration;
	}

	public void setViolationconfiguration(List<ViolationConfiguration> violationconfiguration) {
		this.violationconfiguration = violationconfiguration;
	}

	public void addUser(EnterpriseSystemUser pSpaceUser) {
		users.add(pSpaceUser);
	}

	public void removeUser(EnterpriseSystemUser pSpaceUser) {
		users.remove(pSpaceUser);
	}

	public List<EnterpriseSystemUser> getUsers() {
		return users;
	}

	public void setUsers(List<EnterpriseSystemUser> users) {
		this.users = users;
	}

	public String geteSiteId() {
		return eSiteId;
	}

	public void seteSiteId(String eSiteId) {
		this.eSiteId = eSiteId;
	}

	public String geteSiteName() {
		return eSiteName;
	}

	public void seteSiteName(String eSiteName) {
		this.eSiteName = eSiteName;
	}

	public EnterpriseSiteContext getEnterpriseSiteContext() {
		return enterpriseSiteContext;
	}

	public void setEnterpriseSiteContext(EnterpriseSiteContext enterpriseSiteContext) {
		this.enterpriseSiteContext = enterpriseSiteContext;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public Boolean getCimNotified() {
		return cimNotified;
	}

	public void setCimNotified(Boolean cimNotified) {
		this.cimNotified = cimNotified;
	}

	public Timestamp getCimNotifiedTime() {
		return cimNotifiedTime;
	}

	public void setCimNotifiedTime(Timestamp cimNotifiedTime) {
		this.cimNotifiedTime = cimNotifiedTime;
	}

	public EnterpriseSystem geteSystem() {
		return eSystem;
	}

	public void seteSystem(EnterpriseSystem eSystem) {
		this.eSystem = eSystem;
	}

	public List<UserRequest> getUserRequests() {
		return userRequests;
	}

	public void setUserRequests(List<UserRequest> userRequests) {
		this.userRequests = userRequests;
	}
}
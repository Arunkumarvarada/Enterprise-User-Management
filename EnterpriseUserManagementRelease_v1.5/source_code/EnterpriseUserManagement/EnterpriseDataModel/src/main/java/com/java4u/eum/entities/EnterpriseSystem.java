/**
 * 
 */
package com.java4u.eum.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "EnterpriseSystem")
public class EnterpriseSystem {

	@Id
	// @GeneratedValue(generator = "esystem_id_generator")
	// @GenericGenerator(name = "esystem_id_generator", strategy =
	// "com.altiux.eum.entities.idgenerator.EnterpriseSystemIdGenerator")
	@Column(name = "eSystemId")
	private String eSystemId;

	@Column(name = "eSystemName")
	private String eSystemName;

	@Column(name = "active")
	private Boolean active = true;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Embedded
	private Address address;

	@Column(name = "created")
	private Timestamp created;

	@Column(name = "lastModified")
	private Timestamp lastModified;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eSystem", cascade = CascadeType.REMOVE)
	private List<EnterpriseSite> eSites = new ArrayList<EnterpriseSite>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eSystem", cascade = CascadeType.REMOVE)
	private List<EnterpriseSystemUser> users = new ArrayList<EnterpriseSystemUser>();

	@Column(name = "logoUrl")
	private String logoUrl;

	public String geteSystemId() {
		return eSystemId;
	}

	public void seteSystemId(String eSystemId) {
		this.eSystemId = eSystemId;
	}

	public String geteSystemName() {
		return eSystemName;
	}

	public void seteSystemName(String eSystemName) {
		this.eSystemName = eSystemName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public List<EnterpriseSite> geteSites() {
		return eSites;
	}

	public void seteSites(List<EnterpriseSite> eSites) {
		this.eSites = eSites;
	}

	public List<EnterpriseSystemUser> getUsers() {
		return users;
	}

	public void setUsers(List<EnterpriseSystemUser> users) {
		this.users = users;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public void addEnterpriseSite(EnterpriseSite pSpace) {
		eSites.add(pSpace);
	}

	public void addUser(EnterpriseSystemUser user) {
		users.add(user);
	}

	public EnterpriseSystem(String eSystemId, String eSystemName, Boolean active, Address address, Timestamp created,
			Timestamp lastModified, List<EnterpriseSite> eSites, List<EnterpriseSystemUser> users, String logoUrl) {
		super();
		this.eSystemId = eSystemId;
		this.eSystemName = eSystemName;
		this.active = active;
		this.address = address;
		this.created = created;
		this.lastModified = lastModified;
		this.eSites = eSites;
		this.users = users;
		this.logoUrl = logoUrl;
	}

	@Override
	public String toString() {
		return "EnterpriseSystem [eSystemId=" + eSystemId + ", eSystemName=" + eSystemName + ", active=" + active
				+ ", address=" + address + ", created=" + created + ", lastModified=" + lastModified + ", eSites="
				+ eSites + ", users=" + users + ", logoUrl=" + logoUrl + "]";
	}

	public EnterpriseSystem() {
		super();
	}

}

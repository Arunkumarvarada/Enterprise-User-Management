package com.java4u.eum.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Gateway")
public class Gateway {
	
	// Primary Key.
	@Id @GeneratedValue(generator = "gatewayIdGenerator")
	@GenericGenerator(name = "gatewayIdGenerator", strategy = "com.altiux.eum.entities.idgenerator.GatewayIdGenerator")
	@Column(name= "gatewayId") 
	private String gatewayId;
	
	public String getGatewayId() {
		return gatewayId;
	}

	public void setGatewayId(String gatewayId) {
		this.gatewayId = gatewayId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
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

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public EnterpriseSite getEnterpriseSite() {
		return enterpriseSite;
	}

	public void setEnterpriseSite(EnterpriseSite enterpriseSite) {
		this.enterpriseSite = enterpriseSite;
	}


	@Column(nullable=false, name="entityId", unique=true)
	private String entityId;
	
	@Column(name = "entityName")
	private String entityName;
	
	@Column(name = "macAddress", unique=true)
	private String macAddress;
		
	@Column(name = "created")
	private Timestamp created;
	
	@Column(name = "lastModifiedTime")
	private Timestamp lastModifiedTime;
	
	@Embedded
	private Manufacturer manufacturer;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="gateway")
	private List<Device> devices = new ArrayList<Device>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="eSiteId")
	private EnterpriseSite enterpriseSite;

	public Gateway() {
		
	}
	
	public Gateway(String controllerId, String controllerName,
			Timestamp created, Timestamp lastModifiedTime,
			Manufacturer controllerManufacturer, List<Device> devices) {
		super();
		this.gatewayId = controllerId;
		this.entityName = controllerName;
		this.created = created;
		this.lastModifiedTime = lastModifiedTime;
		this.manufacturer = controllerManufacturer;
		this.devices = devices;
	}


	public void addDevice(Device device){
		devices.add(device);
	}
}

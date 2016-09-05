package com.altiux.eum.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "findDeviceByIdAndSiteId", query = "from Device as d where d.deviceId=:deviceId AND d.enterpriseSite.eSiteId=:eSiteId") })
@Table(name = "Device", uniqueConstraints = { @UniqueConstraint(columnNames = { "nodeId", "gatewayId" }) })
public class Device {

	@Id
	@GeneratedValue(generator = "deviceIdGenerator")
	@GenericGenerator(name = "deviceIdGenerator", strategy = "com.altiux.eum.entities.idgenerator.DeviceIdGenerator")
	@Column(name = "deviceId")
	private String deviceId;

	@Column(name = "nodeId")
	@Nonnull
	private String nodeId;

	@ManyToOne
	@JoinColumn(name = "gatewayId")
	private Gateway gateway;

	@Column(name = "nodeName")
	@Nonnull
	private String nodeName;

	@Column(name = "password")
	private String password;

	@Column(name = "created")
	private Timestamp created;

	@Column(name = "lastmodifiedTime")
	private Timestamp lastmodifiedTime;

	@Embedded
	private GeoLocation location;

	@Embedded
	private Manufacturer manufacturer;

	@Embedded
	private Photo pic;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eSiteId")
	private EnterpriseSite enterpriseSite;

	@Column(name = "isActive")
	private boolean isActive = false;

	public Device() {

	}

	public Device(String deviceId, Gateway controller, String deviceName, String password, Timestamp created,
			Timestamp lastmodifiedTime, GeoLocation location, Manufacturer manufacturer, Photo pic,
			EnterpriseSite enterpriseSite) {
		this.deviceId = deviceId;
		this.gateway = controller;
		this.nodeName = deviceName;
		this.password = password;
		this.created = created;
		this.lastmodifiedTime = lastmodifiedTime;
		this.location = location;
		this.manufacturer = manufacturer;
		this.pic = pic;
		this.enterpriseSite = enterpriseSite;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Gateway getGateway() {
		return gateway;
	}

	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public GeoLocation getLocation() {
		return location;
	}

	public void setLocation(GeoLocation location) {
		this.location = location;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Photo getPic() {
		return pic;
	}

	public void setPic(Photo pic) {
		this.pic = pic;
	}

	public EnterpriseSite getEnterpriseSite() {
		return enterpriseSite;
	}

	public void setEnterpriseSite(EnterpriseSite enterpriseSite) {
		this.enterpriseSite = enterpriseSite;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", nodeId=" + nodeId + ", gateway=" + gateway + ", nodeName=" + nodeName
				+ ", password=" + password + ", created=" + created + ", lastmodifiedTime=" + lastmodifiedTime
				+ ", location=" + location + ", manufacturer=" + manufacturer + ", pic=" + pic + ", enterpriseSite="
				+ enterpriseSite + ", isActive=" + isActive + "]";
	}

}

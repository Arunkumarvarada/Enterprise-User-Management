package com.altiux.eum.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="EnterpriseApp")
public class ParkingApp {

	@Id
	@Column(name="appId")
	private int appId;
	
	@Column(name="appName")
	private String appName;
	
	@Column(name="port")
	private String port;
	
	@Column(name="ip")
	private String ip;
	
	public ParkingApp() {
		
	}

	public ParkingApp(int appId, String appName, String port, String ip) {
		super();
		this.appId = appId;
		this.appName = appName;
		this.port = port;
		this.ip = ip;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
package com.altiux.eum.esystem.dto;

public class ApplicationConfigDTO {

	private String appName;
	
	private int id;
	
	private String ip;
	
	private String port;

	public ApplicationConfigDTO() {
	}

	public ApplicationConfigDTO(String appName, int id, String ip, String port) {
		super();
		this.appName = appName;
		this.id = id;
		this.ip = ip;
		this.port = port;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	
}

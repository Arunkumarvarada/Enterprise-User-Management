package com.altiux.eum.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ViolationConfiguration implements Serializable{
	
	@Column(name="type")
	private String type;
	
	@Column(name="smsNotification")
	private Boolean smsNotification;
	
	public ViolationConfiguration() {
		
	}
	public ViolationConfiguration(String type, Boolean smsNotification) {
		super();
		this.type = type;
		this.smsNotification = smsNotification;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getSmsNotification() {
		return smsNotification;
	}
	public void setSmsNotification(Boolean smsNotification) {
		this.smsNotification = smsNotification;
	}
}
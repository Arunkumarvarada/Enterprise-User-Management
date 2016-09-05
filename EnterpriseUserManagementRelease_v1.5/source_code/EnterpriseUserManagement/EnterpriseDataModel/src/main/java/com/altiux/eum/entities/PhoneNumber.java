package com.altiux.eum.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@SuppressWarnings("serial")
@Embeddable
public class PhoneNumber implements Serializable {
	
    @Column(name="phoneNumber")
	private String phoneNumber;
	@Column(name="type")
	private String type;

	public PhoneNumber(){
		
	}
	
	public PhoneNumber(String phoneNumber, String type) {
		this.phoneNumber = phoneNumber;
		this.type = type;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}

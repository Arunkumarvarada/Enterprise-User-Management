/**
 * 
 */
package com.java4u.eum.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class EnterpriseSiteAddress implements Serializable {

	@Column(name="streetAddress")
	private String streetAddress;
	
	@Column(name="locality")
	private String locality;
	
	@Column(name="postalCode")
	private String postalCode;
	
	public String getStreetAddress() {
		return streetAddress;
	}
	
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public EnterpriseSiteAddress(String streetAddress, String locality, String postalCode) {
		super();
		this.streetAddress = streetAddress;
		this.locality = locality;
		this.postalCode = postalCode;
	}
	
	@Override
	public String toString() {
		return "EnterpriseSiteAddress [streetAddress=" + streetAddress + ", locality=" + locality + ", postalCode="
				+ postalCode + "]";
	}
	public EnterpriseSiteAddress() {
		
	}
	
}

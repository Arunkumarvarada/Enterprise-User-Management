/**
 * 
 */
package com.altiux.eum.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@SuppressWarnings("serial")
@Embeddable
public class Facility implements Serializable {

	@Column(name="facilityType")
	private String facilitytype;
	@Embedded
	private GeoLocation geoLocation;
	@Embedded
	private EnterpriseSiteAddress address;
	public Facility() {
		super();
	}
	public Facility(String type, GeoLocation geoLocation, EnterpriseSiteAddress address) {
		super();
		this.facilitytype = type;
		this.geoLocation = geoLocation;
		this.address = address;
	}
	public String getType() {
		return facilitytype;
	}
	public void setType(String type) {
		this.facilitytype = type;
	}
	public GeoLocation getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}
	public EnterpriseSiteAddress getAddress() {
		return address;
	}
	public void setAddress(EnterpriseSiteAddress address) {
		this.address = address;
	}
	
	
}

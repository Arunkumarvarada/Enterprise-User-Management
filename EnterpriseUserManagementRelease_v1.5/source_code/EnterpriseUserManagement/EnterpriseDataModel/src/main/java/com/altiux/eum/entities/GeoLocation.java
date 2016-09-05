/**
 * 
 */
package com.altiux.eum.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class GeoLocation implements Serializable {
	
	@Column(name = "latitude")
	private Double laltitude;
	
	@Column(name = "longitude")
	private Double longitutde;
	
	@Column(name = "altitude")
	private Double altitude;

	public GeoLocation() {
		super();
	}

	public GeoLocation(Double laltitude, Double longitutde, Double altitude) {
		super();
		this.laltitude = laltitude;
		this.longitutde = longitutde;
		this.altitude = altitude;
	}

	public Double getLaltitude() {
		return laltitude;
	}

	public void setLaltitude(Double laltitude) {
		this.laltitude = laltitude;
	}

	public Double getLongitutde() {
		return longitutde;
	}

	public void setLongitutde(Double longitutde) {
		this.longitutde = longitutde;
	}

	public Double getAltitude() {
		return altitude;
	}

	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}

}

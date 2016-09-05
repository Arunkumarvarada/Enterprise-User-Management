package com.altiux.eum.entities;

/**
 * @author Ankit.A
 *
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@SuppressWarnings("serial")
@Embeddable
public class Specifics implements Serializable {

	@Column(name="demarcated")
	private Boolean demarcated;
	
	@Embedded
	private Facility facility;
	public Specifics() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Specifics(Boolean demarcated, Facility facility) {
		super();
		this.demarcated = demarcated;
		this.facility = facility;
	}
	
	/**
	 * @return the demarcated
	 */
	public Boolean getDemarcated() {
		return demarcated;
	}
	/**
	 * @param demarcated the demarcated to set
	 */
	public void setDemarcated(Boolean demarcated) {
		this.demarcated = demarcated;
	}
	public Facility getFacility() {
		return facility;
	}
	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	
	
}
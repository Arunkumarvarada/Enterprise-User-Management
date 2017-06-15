package com.java4u.eum.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Manufacturer implements Serializable {
    @Column(name="manufacturerName")
	private String name;
	
    @Column(name="manufacturingYear")
	private int year;
	
    @Column(name="serialno")
	private String serialno;
	
	public Manufacturer() {
		
	}
	
	public Manufacturer(String name, int year, String serialno) {
		this.name = name;
		this.year = year;
		this.serialno = serialno;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public String getSerialno() {
		return serialno;
	}
	
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
}

package com.altiux.eum.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@SuppressWarnings("serial")
@Embeddable
public class Photo implements Serializable {
  
	@Column(name="photoUrl")
	private String photoUrl;
	@Column(name="photoType")
	private String photoType;
	
	/**
     * Default Constructor.
     */
	public Photo(){
		
	}
	
   public Photo(String photoUrl, String photoType) {
		this.photoUrl = photoUrl;
		this.photoType = photoType;
		
	}

   public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}
	
}

package com.java4u.eum.dto;

import java.util.List;

public class ApplicationConfigListDTO {
	
	List<ApplicationConfigDTO> applications;

	
	public ApplicationConfigListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApplicationConfigListDTO(List<ApplicationConfigDTO> applications) {
		super();
		this.applications = applications;
	}

	public List<ApplicationConfigDTO> getApplications() {
		return applications;
	}

	public void setApplications(List<ApplicationConfigDTO> applications) {
		this.applications = applications;
	}

	@Override
	public String toString() {
		return "ApplicationConfigListDTO [applications=" + applications + "]";
	}
	
	

}

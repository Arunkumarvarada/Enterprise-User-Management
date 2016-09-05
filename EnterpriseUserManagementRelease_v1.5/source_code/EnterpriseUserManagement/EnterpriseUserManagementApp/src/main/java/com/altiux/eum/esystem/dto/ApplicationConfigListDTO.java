package com.altiux.eum.esystem.dto;

import java.util.List;

public class ApplicationConfigListDTO {
	
	List<ApplicationConfigDTO> applications;

	
	public ApplicationConfigListDTO() {
		super();
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

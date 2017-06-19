package com.java4u.eum.esite.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ModuleListDTO {

	private List<String> modules = new ArrayList<String>();

	public List<String> getModules() {
		return modules;
	}

	public void setModules(List<String> modules) {
		this.modules = modules;
	}

	public ModuleListDTO(List<String> modules) {
		super();
		this.modules = modules;
	}

	public ModuleListDTO() {
		super();
	}
	
}
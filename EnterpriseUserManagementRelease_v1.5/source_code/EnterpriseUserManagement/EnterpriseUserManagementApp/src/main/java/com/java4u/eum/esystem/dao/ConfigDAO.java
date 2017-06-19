package com.java4u.eum.esystem.dao;

import com.java4u.eum.esystem.dto.ApplicationConfigDTO;


public interface ConfigDAO {

	ApplicationConfigDTO getApplicationConfiguration(String appname);
	
	boolean addApplicationConfiguration(final ApplicationConfigDTO configDTO);
	
	boolean updateApplicationConfiguration(final ApplicationConfigDTO configDTO);

}

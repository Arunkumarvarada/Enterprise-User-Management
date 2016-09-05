package com.altiux.eum.esystem.dao;

import com.altiux.eum.esystem.dto.ApplicationConfigDTO;


public interface ConfigDAO {

	ApplicationConfigDTO getApplicationConfiguration(String appname);
	
	boolean addApplicationConfiguration(final ApplicationConfigDTO configDTO);
	
	boolean updateApplicationConfiguration(final ApplicationConfigDTO configDTO);

}

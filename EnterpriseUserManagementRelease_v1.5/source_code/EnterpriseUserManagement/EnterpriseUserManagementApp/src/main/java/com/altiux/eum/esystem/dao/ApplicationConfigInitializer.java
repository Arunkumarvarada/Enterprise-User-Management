package com.altiux.eum.esystem.dao;

import org.springframework.beans.factory.InitializingBean;

import com.altiux.eum.esystem.dto.ApplicationConfigDTO;


public class ApplicationConfigInitializer implements InitializingBean {
	
	private ConfigDAO configDAO;
	
	private ApplicationConfigDTO sdpServerConfig;

	@Override
	public void afterPropertiesSet() throws Exception {
		
		ApplicationConfigDTO dto = configDAO.getApplicationConfiguration(sdpServerConfig.getAppName());
		if(dto == null) {
			configDAO.addApplicationConfiguration(new ApplicationConfigDTO(sdpServerConfig.getAppName(), 
					sdpServerConfig.getId(),sdpServerConfig.getIp(), sdpServerConfig.getPort()));	
		} else {
			configDAO.updateApplicationConfiguration(new ApplicationConfigDTO(sdpServerConfig.getAppName(), 
					sdpServerConfig.getId(),sdpServerConfig.getIp(), sdpServerConfig.getPort()));	
		}
	}

	public void setConfigDAO(ConfigDAO configDAO) {
		this.configDAO = configDAO;
	}

	public void setSdpServerConfig(ApplicationConfigDTO sdpServerConfig) {
		this.sdpServerConfig = sdpServerConfig;
	}
}
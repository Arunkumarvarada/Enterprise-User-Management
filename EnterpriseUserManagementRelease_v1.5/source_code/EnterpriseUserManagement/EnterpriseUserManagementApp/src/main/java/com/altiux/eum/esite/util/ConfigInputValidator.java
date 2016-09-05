package com.altiux.eum.esite.util;

import org.hibernate.SessionFactory;

public class ConfigInputValidator {

	private ConfigSessionFactory configSessionFactory;

	public ConfigSessionFactory getConfigSessionFactory() {
		return configSessionFactory;
	}

	public void setConfigSessionFactory(ConfigSessionFactory configSessionFactory) {
		this.configSessionFactory = configSessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return configSessionFactory.getConfigSessionFactoryBean();
	}

}

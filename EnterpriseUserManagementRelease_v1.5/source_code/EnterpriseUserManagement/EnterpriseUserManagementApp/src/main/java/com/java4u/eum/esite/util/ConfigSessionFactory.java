package com.altiux.eum.esite.util;

import org.hibernate.SessionFactory;

public class ConfigSessionFactory {

	private SessionFactory configSessionFactoryBean;

	public SessionFactory getConfigSessionFactoryBean() {
		return configSessionFactoryBean;
	}

	public void setConfigSessionFactoryBean(SessionFactory configSessionFactoryBean) {
		this.configSessionFactoryBean = configSessionFactoryBean;
	}

}

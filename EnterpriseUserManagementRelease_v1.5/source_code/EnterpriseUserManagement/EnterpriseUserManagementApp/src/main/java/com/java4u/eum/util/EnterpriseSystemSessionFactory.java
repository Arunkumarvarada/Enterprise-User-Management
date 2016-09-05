package com.altiux.eum.util;

import org.hibernate.SessionFactory;

public class EnterpriseSystemSessionFactory {
	
	private SessionFactory parkingSessionFactoryBean;
	
	public SessionFactory getSessionFactory() {
		return parkingSessionFactoryBean;
	}

	public void setParkingSessionFactoryBean(SessionFactory sessionFactory) {
		this.parkingSessionFactoryBean = sessionFactory;
	}
			
	
}

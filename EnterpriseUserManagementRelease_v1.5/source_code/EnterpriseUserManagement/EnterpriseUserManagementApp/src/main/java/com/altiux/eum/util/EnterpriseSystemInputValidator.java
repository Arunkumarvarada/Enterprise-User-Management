package com.altiux.eum.util;

import org.hibernate.SessionFactory;


public class EnterpriseSystemInputValidator{
	
	private EnterpriseSystemSessionFactory parkingCompanySessionFactory;

	public EnterpriseSystemSessionFactory getParkingCompanySessionFactory() {
		return parkingCompanySessionFactory;
	}

	public void setParkingCompanySessionFactory(
			EnterpriseSystemSessionFactory parkingCompanySessionFactory) {
		this.parkingCompanySessionFactory = parkingCompanySessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return parkingCompanySessionFactory.getSessionFactory();
	}
}

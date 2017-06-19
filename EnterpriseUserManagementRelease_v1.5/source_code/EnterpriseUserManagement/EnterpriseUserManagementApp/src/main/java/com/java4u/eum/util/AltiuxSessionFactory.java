package com.java4u.eum.util;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 * 
 * @author Arun
 *
 */
public class AltiuxSessionFactory {
	
	private SessionFactory sessionFactory;
	
	@Autowired
	private AnnotationSessionFactoryBean localSessionFactoryBean;
	
	
	public void setLocalSessionFactoryBean(
			AnnotationSessionFactoryBean localSessionFactoryBean) {
		this.localSessionFactoryBean = localSessionFactoryBean;
		sessionFactory = localSessionFactoryBean.getObject();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}

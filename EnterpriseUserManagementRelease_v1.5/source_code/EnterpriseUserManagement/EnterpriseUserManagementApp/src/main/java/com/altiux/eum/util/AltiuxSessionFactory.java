package com.altiux.eum.util;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 * 
 * @author Ankit Arora
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

package com.altiux.eum.esystem.dao.impl;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.dao.DataAccessException;

import com.altiux.logger.App_logger;
import com.altiux.logger.EModuleName;
import com.altiux.logger.LoggerFactory;

import com.altiux.eum.esystem.dao.ConfigDAO;
import com.altiux.eum.esystem.dto.ApplicationConfigDTO;
import com.altiux.eum.util.EnterpriseSystemInputValidator;
import com.altiux.eum.entities.ParkingApp;
import com.altiux.eum.esite.util.ConfigInputValidator;

public class ConfigDAOImpl implements ConfigDAO {

	private static final App_logger LOGGER = LoggerFactory.getLogger(EModuleName.DATASERVICE);

	private EnterpriseSystemInputValidator enterpriseSystemInputValidator;

	public void setEnterpriseSystemInputValidator(EnterpriseSystemInputValidator enterpriseSystemInputValidator) {
		this.enterpriseSystemInputValidator = enterpriseSystemInputValidator;
	}

	@Override
	public ApplicationConfigDTO getApplicationConfiguration(final String appName) {
		Session session = null;
		ParkingApp application = null;
		try {
			session = enterpriseSystemInputValidator.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from ParkingApp a where a.appName=:appName");
			query.setParameter("appName", appName);
			application = (ParkingApp) query.uniqueResult();
			LOGGER.debug(this.getClass().getSimpleName(), "getApplicationConfiguration",
					"application = " + application);
			session.getTransaction().commit();
		} catch (DataAccessException e) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			session.close();
		}

		if (application != null) {
			ApplicationConfigDTO dto = new ApplicationConfigDTO();
			dto.setAppName(appName);
			dto.setId(application.getAppId());
			dto.setIp(application.getIp());
			dto.setPort(application.getPort());
			return dto;
		}

		return null;
	}

	@Override
	public boolean addApplicationConfiguration(final ApplicationConfigDTO configDTO) {
		Session session = null;
		try {
			session = enterpriseSystemInputValidator.getSessionFactory().openSession();
			session.beginTransaction();
			ParkingApp appConfig = new ParkingApp();
			appConfig.setAppId(configDTO.getId());
			appConfig.setAppName(configDTO.getAppName());
			appConfig.setIp(configDTO.getIp());
			appConfig.setPort(configDTO.getPort());
			session.persist(appConfig);
			LOGGER.debug(this.getClass().getSimpleName(), "addApplicationConfiguration", "appConfig = " + appConfig);
			session.getTransaction().commit();
		} catch (DataAccessException e) {
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			session.close();
		}

		return true;
	}

	@Override
	public boolean updateApplicationConfiguration(ApplicationConfigDTO configDTO) {

		Session session = null;
		ParkingApp application = null;
		try {
			session = enterpriseSystemInputValidator.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from ParkingApp a where a.appId=:appId");
			query.setParameter("appId", configDTO.getId());
			application = (ParkingApp) query.uniqueResult();
			LOGGER.debug(this.getClass().getSimpleName(), "getApplicationConfiguration",
					"application = " + application);
			if (application != null) {
				application.setAppName(configDTO.getAppName());
				application.setIp(configDTO.getIp());
				application.setPort(configDTO.getPort());
			}
			session.getTransaction().commit();
			return true;
		} catch (DataAccessException e) {
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			session.close();
		}
	}
}
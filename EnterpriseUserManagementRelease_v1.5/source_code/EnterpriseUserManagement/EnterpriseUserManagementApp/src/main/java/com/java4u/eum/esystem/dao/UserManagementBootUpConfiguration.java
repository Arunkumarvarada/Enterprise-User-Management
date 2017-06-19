package com.java4u.eum.esystem.dao;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.java4u.eum.esite.dao.UserManagementDAO;
import com.java4u.logger.App_logger;
import com.java4u.logger.EModuleName;
import com.java4u.logger.LoggerFactory;

public class UserManagementBootUpConfiguration implements InitializingBean {

	private static final App_logger logger = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	@Autowired
	private UserManagementDAO dao;

	public void setDao(UserManagementDAO dao) {
		this.dao = dao;
	}

	public void afterPropertiesSet() throws Exception {
		logger.debug(this.getClass().getSimpleName(), "afterPropertiesSet",
				"Intilization begin for adding user and roles");
		try {
			dao.insertRolesandOperations();
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "afterPropertiesSet", e.getMessage());
			e.printStackTrace();
		}
	}

}

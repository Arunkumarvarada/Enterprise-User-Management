package com.altiux.eum.esystem.dao;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.altiux.eum.esite.dao.UserManagementDAO;
import com.altiux.logger.App_logger;
import com.altiux.logger.EModuleName;
import com.altiux.logger.LoggerFactory;

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

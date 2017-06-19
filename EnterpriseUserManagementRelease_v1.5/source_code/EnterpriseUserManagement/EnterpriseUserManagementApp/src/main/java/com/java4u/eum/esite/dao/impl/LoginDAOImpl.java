package com.java4u.eum.esite.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.java4u.commons.exceptions.InvalidInputException;
import com.java4u.commons.exceptions.EnterpriseSiteNotActiveException;
import com.java4u.commons.exceptions.UserNotFoundException;
import com.java4u.eum.entities.EnterpriseSite;
import com.java4u.eum.entities.EnterpriseSystemUser;
import com.java4u.logger.App_logger;
import com.java4u.logger.EModuleName;
import com.java4u.logger.LoggerFactory;

import com.java4u.eum.util.EnterpriseSystemInputValidator;
import com.java4u.eum.esite.dao.LoginDAO;
import com.java4u.eum.esite.dto.LoginCredentialsDTO;
import com.java4u.eum.esite.dto.LoginSuccessDTO;

public class LoginDAOImpl implements LoginDAO {

	/* App_logger instance. */
	private static final App_logger LOGGER = LoggerFactory.getLogger(EModuleName.DATASERVICE);

	private EnterpriseSystemInputValidator enterpriseSystemInputValidator;

	@Override
	public LoginSuccessDTO login(final LoginCredentialsDTO requestDTO) throws InvalidInputException, EnterpriseSiteNotActiveException, UserNotFoundException {
		// TODO Auto-generated method stub
		Session session = null;
		LoginSuccessDTO loginResponse = null;
		boolean problem =false;
		String problemType = null;
		try {
			session = enterpriseSystemInputValidator.getSessionFactory().openSession();
			session.beginTransaction();

			Criteria cr = session.createCriteria(EnterpriseSystemUser.class);
			cr.add(Restrictions.eq("userName", requestDTO.getUserName()));
			cr.add(Restrictions.eq("password", requestDTO.getPassword()));
			EnterpriseSystemUser user = (EnterpriseSystemUser)cr.uniqueResult();
			System.out.println( requestDTO.getUserName()+":"+requestDTO.getPassword());

			Criteria cr1 = session.createCriteria(EnterpriseSite.class);
			cr1.add(Restrictions.eq("eSiteId", requestDTO.getEnterpriseSiteId()));
			EnterpriseSite eSite = (EnterpriseSite) cr1.uniqueResult();
			//boolean validUserForParkingSpace = false;
			if(user!=null && eSite != null){
				if(eSite.getActive()){
					
//					List<EnterpriseSite> parkingSpaces = user.getEnterpriseSites();
//					for(int i= 0; i< parkingSpaces.size(); i++) {
//						if(parkingSpaces.get(i).geteSiteId().equals(requestDTO.getEnterpriseSiteId())) {
//							validUserForParkingSpace = true;
//							break;
//						}
//					}
					
					if(user.getUserName().equals(requestDTO.getUserName()) && user.getPassword().equals(requestDTO.getPassword())){
						loginResponse = new LoginSuccessDTO();
						loginResponse.setUserId(user.getUserId());
						loginResponse.setEntrpriseSystemId(eSite.geteSystem().geteSystemId());
						loginResponse.setEntrpriseSiteId(eSite.geteSiteId());
					} else{
						problem = true;
						problemType = "UserNotFound";
					}
				}
				else{
					problem = true;
					problemType = "EntrpriseSiteNotActive";
				}
			}else{
				problem = true;
				problemType = "UserNotFound";
			}
			session.getTransaction().commit();
		} catch(DataAccessException e) {
			return null;
		} catch(Exception e) {
			return null;
		} finally {
			session.close();
		}
		// 
		if(problem){
			if(problemType.equals("EntrpriseSiteNotActive"))
				throw new EnterpriseSiteNotActiveException("Entrprise Site associated with this account is not acitve. No Permission To Login.");
			if(problemType.equals("UserNotFound"))
				throw new UserNotFoundException("Specified Account Details Incorrect");
		}

		return loginResponse;
	}


	public void setParkingCompanyInputValidator(final EnterpriseSystemInputValidator enterpriseSystemInputValidator) {
		this.enterpriseSystemInputValidator = enterpriseSystemInputValidator;
	}

}

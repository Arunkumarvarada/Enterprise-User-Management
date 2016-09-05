package com.altiux.eum.esite.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.commons.exceptions.EnterpriseSiteNotActiveException;
import com.altiux.commons.exceptions.UserNotFoundException;
import com.altiux.eum.entities.EnterpriseSite;
import com.altiux.eum.entities.EnterpriseSystemUser;
import com.altiux.logger.App_logger;
import com.altiux.logger.EModuleName;
import com.altiux.logger.LoggerFactory;

import com.altiux.eum.util.EnterpriseSystemInputValidator;
import com.altiux.eum.esite.dao.LoginDAO;
import com.altiux.eum.esite.dto.LoginCredentialsDTO;
import com.altiux.eum.esite.dto.LoginSuccessDTO;

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

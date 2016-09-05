package com.altiux.eum.esystem.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.commons.exceptions.UserAlreadyAvailableException;
import com.altiux.commons.exceptions.UserNotFoundException;
import com.altiux.logger.App_logger;
import com.altiux.logger.EModuleName;
import com.altiux.logger.LoggerFactory;
import com.altiux.eum.entities.Email;
import com.altiux.eum.entities.EnterpriseSite;
import com.altiux.eum.entities.EnterpriseSystem;
import com.altiux.eum.entities.EnterpriseSystemUser;
import com.altiux.eum.entities.Operations;
import com.altiux.eum.entities.PhoneNumber;
import com.altiux.eum.entities.Photo;
import com.altiux.eum.entities.Roles;
import com.altiux.eum.entities.User;
import com.altiux.eum.entities.UserRequest;
import com.altiux.eum.esystem.dao.UserManagementDAO;
import com.altiux.eum.esystem.dto.AllAsstManagersResponseDTO;
import com.altiux.eum.esystem.dto.AllEnterpriseSiteUsersDTO;
import com.altiux.eum.esystem.dto.AllManagersResponseDTO;
import com.altiux.eum.esystem.dto.AllUsersResponseDTO;
import com.altiux.eum.esystem.dto.AsstManagerDTO;
import com.altiux.eum.esystem.dto.EmailDTO;
import com.altiux.eum.esystem.dto.EnterpriseSitesListDTO;
import com.altiux.eum.esystem.dto.ManagersDTO;
import com.altiux.eum.esystem.dto.OperationsDTO;
import com.altiux.eum.esystem.dto.EnterpriseSystemLEODTO;
import com.altiux.eum.esystem.dto.EnterpriseSitesListResponseDTO;
import com.altiux.eum.esystem.dto.PhoneNumberDTO;
import com.altiux.eum.esystem.dto.PhotoDTO;
import com.altiux.eum.esystem.dto.UserAdditionRequestDTO;
import com.altiux.eum.esystem.dto.UserAdditionResponseDTO;
import com.altiux.eum.esystem.dto.UserAssigmentRequestDTO;
import com.altiux.eum.esystem.dto.UserAssigmentResponseDTO;
import com.altiux.eum.esystem.dto.UserDeletionRequestDTO;
import com.altiux.eum.esystem.dto.UserDeletionResponseDTO;
import com.altiux.eum.util.EnterpriseSystemInputValidator;

public class UserManagementDAOImpl implements UserManagementDAO {

	private static final int READ_PERMISSION = 1;
	private static final int EXECUTE_PERMISSION = 2;
	private static final int WRITE_PERMISSION = 3;

	private static final App_logger logger = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	private EnterpriseSystemInputValidator enterpriseSystemInputValidator;

	public void setParkingSiteInputValidator(EnterpriseSystemInputValidator enterpriseSystemInputValidator) {
		this.enterpriseSystemInputValidator = enterpriseSystemInputValidator;
	}

	@Override
	@Transactional
	public void insertRolesandOperations() {
		logger.debug(this.getClass().getSimpleName(), "insertRolesandOperations()",
				"Inside the method--- insertRolesandOperations()");
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		try {
			List<Operations> operationList = getOperationsFromDB(session);
			System.out.println("Operation Size >>" + operationList.size());
			operationList.toString();
			List<Roles> roleList = getRolesFromDB(session);
			System.out.println("Roles Size >>" + roleList.size());
			roleList.toString();
			if (operationList.isEmpty()) {
				System.out.println("Insertion of Operations started");
				insertOperations(session);
				logger.debug(this.getClass().getSimpleName(), "insertRolesandOperations()",
						"Inserted the operations into DB at Boot up");
			}
			if (roleList.isEmpty()) {
				System.out.println("Insertion of Roles started");
				insertRoles(session);
				logger.debug(this.getClass().getSimpleName(), "insertRolesandOperations()",
						"Inserted the roles into DB at Boot up");
			}
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
	}

	private List<Roles> insertRoles(Session session) {
		List<Roles> roles = null;
		List<Operations> adminRoleOperations = getOperationsBasedOnRole("adminoperations", session);
		List<Operations> managerRoleOperations = getOperationsBasedOnRole("managerrole", session);
		List<Operations> asstManagerRoleOperations = getOperationsBasedOnRole("ManagerRole", session);
		List<Operations> lawEnforcementOfficerRoleOperations = getOperationsBasedOnRole("lawenforcementofficerrole",
				session);
		System.out.println("Operator Roles");
		try {
			session.beginTransaction();
			// Add ADMIN role
			Roles adminRole = new Roles();
			adminRole.setRoleName("PCADMIN");

			adminRole.setOperations(adminRoleOperations);
			session.save(adminRole);
			for (Operations operation : adminRoleOperations) {
				operation.addRole(adminRole);
				session.save(operation);
			}

			// Add MANAGER role
			Roles managerRole = new Roles();
			managerRole.setRoleName("MANAGER");

			managerRole.setOperations(managerRoleOperations);
			session.save(managerRole);
			for (Operations operation : managerRoleOperations) {
				operation.addRole(managerRole);
				session.save(operation);
			}

			// Add ASST MANAGER role
			Roles asstManagerRole = new Roles();
			asstManagerRole.setRoleName("ASST MANAGER");
			asstManagerRole.setOperations(asstManagerRoleOperations);
			session.save(asstManagerRole);
			for (Operations operation : asstManagerRoleOperations) {
				operation.addRole(asstManagerRole);
				session.save(operation);
			}

			// Add LAW ENFORCEMENT MANAGER role
			Roles leoRole = new Roles();
			leoRole.setRoleName("LAW ENFORCEMENT OFFICER");
			leoRole.setOperations(lawEnforcementOfficerRoleOperations);
			session.save(leoRole);
			for (Operations operation : lawEnforcementOfficerRoleOperations) {
				operation.addRole(adminRole);
				session.save(operation);
			}

			session.getTransaction().commit();
			roles = getRolesFromDB(session);
			return roles;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	private List<Operations> getOperationsBasedOnRole(String role, Session session) {
		List<Operations> operationList = getOperationsFromDB(session);
		List<Operations> response = new ArrayList<Operations>();
		switch (role) {
		case "alloperations":
		case "AllOperations":
			response = operationList;
			break;
		case "adminoperations":
		case "AdminOperations":
			response = getAdminOperationsFromDB(session);
			break;
		case "managerrole":
		case "ManagerRole":
			response = getManagerOperationsFromDB(session);
			break;
		case "asstmanagerrole":
		case "AsstManagerRole":
			response = getAsstManagerOperationsFromDB(session);
			break;
		case "LawEnforcementOfficerRole":
		case "lawenforcementofficerrole":
			response = getLEOOperationsFromDB(session);
			break;
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	private List<Operations> getAdminOperationsFromDB(Session session) {
		List<Operations> operations = null;
		session.beginTransaction();
		try {
			Criteria cr = session.createCriteria(Operations.class);
			cr.add(Restrictions.eq("writePermission", true));
			operations = (List<Operations>) cr.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
		return operations;
	}

	@SuppressWarnings("unchecked")
	private List<Operations> getManagerOperationsFromDB(Session session) {
		List<Operations> operations = null;
		session.beginTransaction();
		try {
			Criteria cr = session.createCriteria(Operations.class);
			cr.add(Restrictions.eq("writePermission", true));
			operations = (List<Operations>) cr.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
		return operations;
	}

	private List<Operations> getAsstManagerOperationsFromDB(Session session) {
		List<Operations> operations = getManagerOperationsFromDB(session);
		operations.remove(8);
		// Operations userManagement = getMatchingOperations("UserManagement",
		// true, false, false, session);
		// operations.add(userManagement);
		return operations;
	}

	@SuppressWarnings("unchecked")
	private List<Operations> getLEOOperationsFromDB(Session session) {
		List<Operations> operations = null;
		session.beginTransaction();
		try {
			Criteria cr = session.createCriteria(Operations.class);
			cr.add(Restrictions.eq("readPermission", true));
			operations = (List<Operations>) cr.list();
			operations.remove(5);
			operations.remove(8);
			// Operations userManagement =
			// getMatchingOperations("UserManagement", true, false, false,
			// session);
			// operations.add(userManagement);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
		return operations;
	}

	private List<Operations> insertOperations(Session session) {
		List<Operations> operations = null;
		try {
			List<OperationsDTO> operationsList = getListOfOperations();
			System.out.println(" all Operations size : " + operationsList.size());
			operations = addOperations(operationsList, session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return operations;
	}

	private String getModulePermissionType(int permissionType) {
		switch (permissionType) {
		case 1:
			return "readPermission";
		case 2:
			return "executePermission";
		case 3:
			return "writePermission";
		}
		return null;
	}

	private List<Operations> addOperations(List<OperationsDTO> operations, Session session) {
		session.beginTransaction();
		List<Operations> operationList = null;
		try {
			for (OperationsDTO dto : operations) {
				Operations operation = new Operations();
				operation.setExecutePermission(dto.getExecutePermission());
				operation.setModuleOperationName(dto.getModuleOperationName());
				operation.setReadPermission(dto.getReadPermission());
				operation.setWritePermission(dto.getWritePermission());
				session.persist(operation);
				session.beginTransaction().commit();
			}
			operationList = getOperationsFromDB(session);
			return operationList;
		} catch (Exception e) {
			session.getTransaction().rollback();
			return null;
		}
	}

	private List<OperationsDTO> getListOfOperations() {
		List<OperationsDTO> inputData = new ArrayList<>();

		// creation of operation input data
		OperationsDTO liveViewRead = new OperationsDTO("LiveView", true, false, false);
		OperationsDTO liveView = new OperationsDTO("LiveView", false, true, false);
		OperationsDTO liveViewFull = new OperationsDTO("LiveView", false, false, true);

		OperationsDTO dashBoardRead = new OperationsDTO("DashBoard", true, false, false);
		OperationsDTO dashBoardView = new OperationsDTO("DashBoard", false, true, false);
		OperationsDTO dashBoardFull = new OperationsDTO("DashBoard", false, false, true);

		OperationsDTO inventoryRead = new OperationsDTO("Inventory", true, false, false);
		OperationsDTO inventoryView = new OperationsDTO("Inventory", false, true, false);
		OperationsDTO inventoryFull = new OperationsDTO("Inventory", false, false, true);

		OperationsDTO sensorDataRead = new OperationsDTO("SensorData", true, false, false);
		OperationsDTO sensorDataView = new OperationsDTO("SensorData", false, true, false);
		OperationsDTO sensorDataFull = new OperationsDTO("SensorData", false, false, true);

		OperationsDTO profileRead = new OperationsDTO("Profile", true, false, false);
		OperationsDTO profileView = new OperationsDTO("Profile", false, true, false);
		OperationsDTO profileFull = new OperationsDTO("Profile", false, false, true);

		OperationsDTO reportsRead = new OperationsDTO("Reports", true, false, false);
		OperationsDTO reportsView = new OperationsDTO("Reports", false, true, false);
		OperationsDTO reportsFull = new OperationsDTO("Reports", false, false, true);

		OperationsDTO configurationRead = new OperationsDTO("Configuration", true, false, false);
		OperationsDTO configurationView = new OperationsDTO("Configuration", false, true, false);
		OperationsDTO configurationFull = new OperationsDTO("Configuration", false, false, true);

		OperationsDTO violationsRead = new OperationsDTO("Violations", true, false, false);
		OperationsDTO violationsView = new OperationsDTO("Violations", false, true, false);
		OperationsDTO violationsFull = new OperationsDTO("Violations", false, false, true);

		OperationsDTO userManagementRead = new OperationsDTO("UserManagement", true, false, false);
		OperationsDTO userManagementView = new OperationsDTO("UserManagement", false, true, false);
		OperationsDTO userManagementFull = new OperationsDTO("UserManagement", false, false, true);

		inputData.add(liveViewRead);
		inputData.add(liveView);
		inputData.add(liveViewFull);

		inputData.add(dashBoardRead);
		inputData.add(dashBoardView);
		inputData.add(dashBoardFull);

		inputData.add(inventoryRead);
		inputData.add(inventoryView);
		inputData.add(inventoryFull);

		inputData.add(sensorDataRead);
		inputData.add(sensorDataView);
		inputData.add(sensorDataFull);

		inputData.add(profileRead);
		inputData.add(profileView);
		inputData.add(profileFull);

		inputData.add(reportsRead);
		inputData.add(reportsView);
		inputData.add(reportsFull);

		inputData.add(configurationRead);
		inputData.add(configurationView);
		inputData.add(configurationFull);

		inputData.add(violationsRead);
		inputData.add(violationsView);
		inputData.add(violationsFull);

		inputData.add(userManagementRead);
		inputData.add(userManagementView);
		inputData.add(userManagementFull);

		return inputData;
	}

	@SuppressWarnings("unchecked")
	private List<Roles> getRolesFromDB(Session session) {
		List<Roles> rolesList = null;
		try {
			Criteria cr = session.createCriteria(Roles.class);
			rolesList = (List<Roles>) cr.list();
			return rolesList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private List<Operations> getOperationsFromDB(Session session) {
		List<Operations> operationList = null;
		try {
			Criteria cr = session.createCriteria(Operations.class);
			operationList = (List<Operations>) cr.list();
			return operationList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UserDeletionResponseDTO deleteUser(UserDeletionRequestDTO dto)
			throws UserNotFoundException, InvalidInputException {
		logger.debug(this.getClass().getSimpleName(), "deleteUser()", "Inside the User Deletion Method!!");
		UserDeletionResponseDTO response = new UserDeletionResponseDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		session.beginTransaction();

		if (!isEnterpriseSystemExists(dto.getEnterpriseSystemId(), session)) {
			throw new InvalidInputException("Enterprise System doesn't exists with id: " + dto.getEnterpriseSystemId());
		}
		if (!isAdmin(dto.getAdminUserId(), session)) {
			throw new InvalidInputException("Admin user is not valid  " + dto.getAdminUserId());
		}

		User user = null;
		user = getUser(dto.getUserId(), session);
		if (user == null || StringUtils.isBlank(user.getDisplayName())) {
			logger.debug(this.getClass().getSimpleName(), "deleteUser()", "UserID  :" + user.getUserId());
			throw new UserNotFoundException("User ID does not exists in the Database");
		}

		try {
			if (isAdmin(dto.getAdminUserId(), session)) {
				if (dto.getEnterpriseSystemId() != null && dto.getUserId() != null) {
					user = getUser(dto.getUserId(), session);
					if (user != null && user instanceof EnterpriseSystemUser) {
						EnterpriseSystemUser pcUser = (EnterpriseSystemUser) user;
						List<EnterpriseSite> spaces = pcUser.getEnterpriseSites();
						for (int i = 0; i < spaces.size(); i++) {
							spaces.get(i).removeUser(pcUser);
						}
						pcUser.geteSystem().getUsers().remove(user);
						
						String userId = user.getUserId();
						logger.debug(this.getClass().getSimpleName(), "deleteUser()", "UserID  :" + user.getUserId());
						session.delete(pcUser);
						session.getTransaction().commit();
						response.setResponse("User Deletion was Succesful!!!");
						response.setAdminUserId(dto.getAdminUserId());
						response.setEnterpriseSystemId(dto.getEnterpriseSystemId());
						response.setUserId(userId);
					}
				}
			}
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "deleteUser()", "Exception occured in the User Deletion!!!");
			e.printStackTrace();
			session.getTransaction().commit();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	private User getUser(String userId, Session session) {
		User user = null;
		try {
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("userId", userId));
			user = (User) cr.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	private boolean isAdmin(String adminUserId, Session session) {
		try {
			User user = getUser(adminUserId, session);
			if (user.getRole().equalsIgnoreCase("EnterpriseSuperAdmin")||user.getRole().equalsIgnoreCase("SiteSuperAdmin")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private User getUserByUserName(String userName, Session session) {
		User user = null;
		try {
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("userName", userName));
			user = (User) cr.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	private Timestamp getCurrentTimeStamp() {
		java.util.Date date = new java.util.Date();
		return new Timestamp(date.getTime());
	}

	@Override
	public UserAdditionResponseDTO createUser(UserAdditionRequestDTO requestDTO, String enterpriseSystemId,
			String adminId) throws UserAlreadyAvailableException, InvalidInputException {
		logger.debug(this.getClass().getSimpleName(), "createUser()", "Inside the User Creation Method!!");
		UserAdditionResponseDTO response = new UserAdditionResponseDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		session.beginTransaction();
		User user = getUserByUserName(requestDTO.getUserName(), session);
		if(!isEnterpriseSystemExists(enterpriseSystemId, session)){
			throw new InvalidInputException("Enterprise System does not exists with Id:  " + enterpriseSystemId);
		}
		if (!isAdmin(adminId, session)) {
			throw new InvalidInputException("Invalid Admin Id:  " + adminId);
		}
		if (user != null && !user.getUserName().trim().isEmpty()) {
			throw new UserAlreadyAvailableException("UserName already Exists");
		}
		try {
			EnterpriseSystemUser pcUser = new EnterpriseSystemUser();
			pcUser.setActive(requestDTO.isActive());
			pcUser.setCreated(getCurrentTimeStamp());
			pcUser.setDisplayName(requestDTO.getDisplayName());
			List<Email> emails = getEmails(requestDTO.getEmails());
			List<PhoneNumber> phone = getPhoneNumbers(requestDTO.getPhoneNumbers());
			List<Photo> pics = getPics(requestDTO.getPics());
			pcUser.setEmails(emails);
			pcUser.setPhoneNumbers(phone);
			pcUser.setPics(pics);
			pcUser.setLastModifiedTime(getCurrentTimeStamp());
			pcUser.setFamilyName(requestDTO.getFamilyName());
			pcUser.setFormattedName(requestDTO.getFormattedName());
			pcUser.setGivenName(requestDTO.getGivenName());
			pcUser.setHonorificPrifix(requestDTO.getHonorificPrifix());
			pcUser.setMiddleName(requestDTO.getMiddleName());
			pcUser.setNickName(requestDTO.getNickName());
			pcUser.setPassword(requestDTO.getPassword());
			pcUser.setUserName(requestDTO.getUserName());

			if (requestDTO.getRole() != null) {
				Roles role = getRole(session, requestDTO.getRole());
				logger.debug(this.getClass().getSimpleName(), "createUser()", role.getRoleName());
				pcUser.setRoles(role);
				List<Operations> operations = role.getOperations();
				logger.debug(this.getClass().getSimpleName(), "createUser()",
						" Size of Operations: " + operations.size());
				for (Operations operation : operations) {
					logger.debug(this.getClass().getSimpleName(), "createUser()",
							" Operations ID add:::  " + operation.getOperationId());
					pcUser.addPermittedOperations(operation.getOperationId());
				}
			}
			EnterpriseSystem eSystem = getEnterpriseSystemDetails(session, enterpriseSystemId);
			pcUser.seteSystem(eSystem);
			pcUser.setRole(requestDTO.getRole());

			// ParkingSpace pSpace = getParkingSpaceDetails(session,
			// parkingSpaceId);
			// pcUser.addParkingSpace(pSpace);

			// pSpace.addUser(pcUser);
			session.save(pcUser);
			// creating new UserRequest
			EnterpriseSystemUser admin = getEnterpriseSystemAdmin(enterpriseSystemId, session);
			UserRequest userRequest = new UserRequest();
			userRequest.setCompletedTime(getCurrentTimeStamp());
			userRequest.setOperatedUserId(pcUser.getUserId());
			userRequest.setEnterpriseSystemUser(admin);
			userRequest.setOperationType("ADD");
			userRequest.setRequestState("COMPLETED");
			userRequest.setCreatedTime(getCurrentTimeStamp());
			session.save(userRequest);
			logger.debug(this.getClass().getSimpleName(), "createUser()", "UserID  :" + pcUser.getUserId());
			session.getTransaction().commit();
			response = convertToUserAdditionResponse(pcUser.getUserId(), requestDTO);
			logger.debug(this.getClass().getSimpleName(), "createUser()", "UserID 2 :" + pcUser.getUserId());

		} catch (Exception e) {
			session.getTransaction().rollback();
			logger.error(this.getClass().getSimpleName(), "createUser()", "Exception occured in the User Creation!!!");
			e.printStackTrace();
			return null;
		} finally {
			// session.flush();
			session.close();
		}
		return response;
	}

	private EnterpriseSystemUser getEnterpriseSystemAdmin(String enterpriseSystemId, Session session) {
		EnterpriseSystemUser user = new EnterpriseSystemUser();
		try {
			Criteria cr = session.createCriteria(EnterpriseSystemUser.class);
			cr.add(Restrictions.eq("role", "EnterpriseSuperAdmin"));
			cr.add(Restrictions.eq("eSystem.eSystemId", enterpriseSystemId));
			user = (EnterpriseSystemUser) cr.uniqueResult();
			if (user != null) {
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;

	}

	private EnterpriseSystem getEnterpriseSystemDetails(Session session, String enterpriseSystemId) {
		EnterpriseSystem eSystem = new EnterpriseSystem();
		Criteria cr = session.createCriteria(EnterpriseSystem.class);
		cr.add(Restrictions.eq("eSystemId", enterpriseSystemId));
		eSystem = (EnterpriseSystem) cr.uniqueResult();
		return eSystem;
	}

	private UserAdditionResponseDTO convertToUserAdditionResponse(String userID, UserAdditionRequestDTO psUser) {
		UserAdditionResponseDTO userData = new UserAdditionResponseDTO();
		try {
			logger.debug(this.getClass().getSimpleName(), "convertToUserAdditionResponse",
					"Inside the method of convertion Method");
			userData.setActive(psUser.isActive());
			String timeStamp = getStringFormatTimeStamp(getCurrentTimeStamp());
			userData.setCreated(timeStamp);
			userData.setDisplayName(psUser.getDisplayName());
			userData.setEmails(psUser.getEmails());
			userData.setPhoneNumbers(psUser.getPhoneNumbers());
			userData.setPics(psUser.getPics());
			userData.setFamilyName(psUser.getFamilyName());
			userData.setFormattedName(psUser.getFormattedName());
			userData.setGivenName(psUser.getGivenName());
			userData.setHonorificPrifix(psUser.getHonorificPrifix());
			String lastModifiedTime = getStringFormatTimeStamp(getCurrentTimeStamp());
			userData.setLastModifiedTime(lastModifiedTime);
			userData.setMiddleName(psUser.getMiddleName());
			userData.setNickName(psUser.getNickName());
			userData.setPassword("");
			userData.setRole(psUser.getRole());
			userData.setUserId(userID);
			userData.setUserName(psUser.getUserName());
			logger.debug(this.getClass().getSimpleName(), "convertToUserAdditionResponse",
					"Exited the method of convertion Method");

		} catch (Throwable e) {
			logger.debug(this.getClass().getSimpleName(), "convertToUserAdditionResponse",
					"Error in the method of convertion Method");

			e.printStackTrace();
		}
		return userData;
	}

	private List<Email> getEmails(List<EmailDTO> list) {
		List<Email> response = new ArrayList<>();
		for (EmailDTO email : list) {
			Email emailResp = new Email();
			emailResp.setEmail(email.getEmail());
			emailResp.setType(email.getType());
			response.add(emailResp);
		}
		return response;
	}

	private List<PhoneNumber> getPhoneNumbers(List<PhoneNumberDTO> phoneNumberDto) {
		List<PhoneNumber> response = new ArrayList<>();
		for (PhoneNumberDTO phone : phoneNumberDto) {
			PhoneNumber phoneNumberResp = new PhoneNumber();
			phoneNumberResp.setPhoneNumber(phone.getPhoneNumber());
			phoneNumberResp.setType(phone.getType());
			response.add(phoneNumberResp);
		}
		return response;
	}

	private List<Photo> getPics(List<PhotoDTO> photoDto) {
		List<Photo> response = new ArrayList<>();
		for (PhotoDTO photo : photoDto) {
			Photo photoResp = new Photo();
			photoResp.setPhotoType(photo.getPhotoType());
			photoResp.setPhotoUrl(photo.getPhotoUrl());
			response.add(photoResp);
		}
		return response;
	}

	private EnterpriseSite getEnterpriseSiteDetails(Session session, String eSiteId) {
		EnterpriseSite eSite = new EnterpriseSite();
		try {
			Criteria cr = session.createCriteria(EnterpriseSite.class);
			cr.add(Restrictions.eq("eSiteId", eSiteId));
			eSite = (EnterpriseSite) cr.uniqueResult();
			return eSite;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Roles getRole(Session session, String roleName) {
		try {
			Criteria cr = session.createCriteria(Roles.class);
			cr.add(Restrictions.eq("roleName", roleName));
			Roles role = (Roles) cr.uniqueResult();
			if (role != null) {
				return role;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public AllManagersResponseDTO getAllEnterpriseSystemManagers(String enterpriseSystemId, Integer offset,
			Integer limit) throws InvalidInputException {
		AllManagersResponseDTO response = new AllManagersResponseDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSystemExists(enterpriseSystemId, session)) {
			throw new InvalidInputException("Parking Company doesn't exists with id: " + enterpriseSystemId);
		}
		try {
			session.beginTransaction();
			response.setEnterpriseSystemId(enterpriseSystemId);
			List<ManagersDTO> userResponse = getParkingCompanyManagers(enterpriseSystemId, session, offset, limit);
			response.setUserResponse(userResponse);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	private List<ManagersDTO> getParkingCompanyManagers(String parkingCompanyId, Session session, Integer offset,
			Integer limit) {
		List<ManagersDTO> pcManagers = new ArrayList<ManagersDTO>();
		try {
			Criteria criteria = session.createCriteria(EnterpriseSystemUser.class);
			criteria.add(Restrictions.eq("eSystem.eSystemId", parkingCompanyId));
			criteria.add(Restrictions.eq("role", "Manager"));
			if ((offset == null) && (limit == null)) {

			} else {
				criteria.setFirstResult(offset);
				criteria.setMaxResults(limit);
			}
			List<EnterpriseSystemUser> managers = (List<EnterpriseSystemUser>) criteria.list();
			pcManagers = convertToManagerDTO(managers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pcManagers;
	}

	private List<ManagersDTO> convertToManagerDTO(List<EnterpriseSystemUser> managers) {
		List<ManagersDTO> managersList = new ArrayList<>();
		if (managers != null) {
			for (EnterpriseSystemUser user : managers) {
				ManagersDTO manager = new ManagersDTO();
				manager.setFirstName(user.getGivenName());
				manager.setLastName(user.getFamilyName());
				manager.setUserId(user.getUserId());
				manager.setUsername(user.getUserName());
				managersList.add(manager);
			}
		}
		return managersList;
	}

	@Override
	@Transactional
	public AllUsersResponseDTO getAllEnterpriseSystemUsers(String enterpriseSystemId, Integer offset, Integer limit)
			throws InvalidInputException {
		AllUsersResponseDTO response = new AllUsersResponseDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSystemExists(enterpriseSystemId, session)) {
			throw new InvalidInputException("Enterprise System doesn't exists with id: " + enterpriseSystemId);
		}
		try {
			session.beginTransaction();
			List<EnterpriseSystemUser> users = getAllPCUsers(enterpriseSystemId, session);
			response.setTotalRecords(users.size());
			List<UserAdditionResponseDTO> userResponse = getParkingCompanyUsers(enterpriseSystemId, offset, limit,
					session);
			response.setEnterpriseSystemId(enterpriseSystemId);
			response.setUserResponse(userResponse);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	private boolean isEnterpriseSystemExists(String enterpriseSystemId, Session session) {
		if (enterpriseSystemId != null) {
			EnterpriseSystem enterpriseSystem = (EnterpriseSystem) session.get(EnterpriseSystem.class,
					enterpriseSystemId);
			if (enterpriseSystem.geteSystemId() != null) {
				return true;
			}
		}
		return false;
	}

	private boolean isEnterpriseSiteExists(String enterpriseSiteId, Session session) {
		if (enterpriseSiteId != null) {
			EnterpriseSite eSite = (EnterpriseSite) session.get(EnterpriseSite.class, enterpriseSiteId);
			if (eSite.geteSiteId() != null) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private List<EnterpriseSystemUser> getAllPCUsers(String enterpriseSystemId, Session session) {
		List<EnterpriseSystemUser> users;
		try {
			Criteria criteria = session.createCriteria(EnterpriseSystemUser.class);
			criteria.add(Restrictions.eq("eSystem.eSystemId", enterpriseSystemId));
			criteria.add(Restrictions.ne("role", "PCADMIN"));
			users = (List<EnterpriseSystemUser>) criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	private List<UserAdditionResponseDTO> getParkingCompanyUsers(String enterpriseSystemId, Integer offset,
			Integer limit, Session session) {
		List<UserAdditionResponseDTO> pcUsers = new ArrayList<UserAdditionResponseDTO>();
		List<EnterpriseSystemUser> users = null;
		try {
			Criteria criteria = session.createCriteria(EnterpriseSystemUser.class);
			criteria.add(Restrictions.eq("eSystem.eSystemId", enterpriseSystemId));
			criteria.add(Restrictions.ne("role", "EnterpriseSuperAdmin"));
			if ((offset == null) && (limit == null)) {

			} else {
				criteria.setFirstResult(offset);
				criteria.setMaxResults(limit);
			}
			users = (List<EnterpriseSystemUser>) criteria.list();

			if (!users.isEmpty()) {
				pcUsers = convertToUsersAdditionResponseDTO(users);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pcUsers;
	}

	private List<UserAdditionResponseDTO> convertToUsersAdditionResponseDTO(List<EnterpriseSystemUser> managers) {
		logger.debug(this.getClass().getSimpleName(), "convertToUsersAdditionResponseDTO()",
				"Inside the method convertToUsersAdditionResponseDTO()");
		List<UserAdditionResponseDTO> usersList = new ArrayList<>();
		if (managers != null) {
			for (EnterpriseSystemUser user : managers) {
				UserAdditionResponseDTO userResponse = new UserAdditionResponseDTO();
				userResponse.setActive(user.isActive());
				userResponse.setCreated(getStringFormatTimeStamp(user.getCreated()));
				userResponse.setDisplayName(user.getDisplayName());
				List<EmailDTO> emails = getEmailDTO(user.getEmails());
				List<PhoneNumberDTO> phoneNumbers = getPhoneNumberDTO(user.getPhoneNumbers());
				List<PhotoDTO> pics = getPhotoDTO(user.getPics());
				userResponse.setEmails(emails);
				userResponse.setFamilyName(user.getFamilyName());
				userResponse.setFormattedName(user.getFormattedName());
				userResponse.setGivenName(user.getGivenName());
				userResponse.setHonorificPrifix(user.getHonorificPrifix());
				userResponse.setLastModifiedTime(getStringFormatTimeStamp(user.getLastModifiedTime()));
				userResponse.setMiddleName(user.getMiddleName());
				userResponse.setNickName(user.getNickName());
				userResponse.setPassword("");
				userResponse.setPhoneNumbers(phoneNumbers);
				userResponse.setPics(pics);
				userResponse.setRole(user.getRole());
				userResponse.setUserId(user.getUserId());
				userResponse.setUserName(user.getUserName());
				usersList.add(userResponse);
			}
		}
		logger.debug(this.getClass().getSimpleName(), "convertToUsersAdditionResponseDTO()",
				"end of the method convertToUsersAdditionResponseDTO()");
		return usersList;

	}

	private String getStringFormatTimeStamp(Timestamp created) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").format(created);
		return timeStamp;
	}

	private List<EmailDTO> getEmailDTO(List<Email> emailDto) {
		List<EmailDTO> response = new ArrayList<>();
		for (Email email : emailDto) {
			EmailDTO emailResp = new EmailDTO();
			emailResp.setEmail(email.getEmail());
			emailResp.setType(email.getType());
			response.add(emailResp);
		}
		return response;
	}

	private List<PhoneNumberDTO> getPhoneNumberDTO(List<PhoneNumber> phoneNumberDto) {
		List<PhoneNumberDTO> response = new ArrayList<>();
		for (PhoneNumber phone : phoneNumberDto) {
			PhoneNumberDTO phoneNumberResp = new PhoneNumberDTO();
			phoneNumberResp.setPhoneNumber(phone.getPhoneNumber());
			phoneNumberResp.setType(phone.getType());
			response.add(phoneNumberResp);
		}
		return response;
	}

	private List<PhotoDTO> getPhotoDTO(List<Photo> photoDto) {
		List<PhotoDTO> response = new ArrayList<>();
		for (Photo photo : photoDto) {
			PhotoDTO photoResp = new PhotoDTO();
			photoResp.setPhotoType(photo.getPhotoType());
			photoResp.setPhotoUrl(photo.getPhotoUrl());
			response.add(photoResp);
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public AllUsersResponseDTO getAllEnterpriseSystemLEOs(String enterpriseSystemId, Integer offset, Integer limit)
			throws InvalidInputException {
		logger.debug(this.getClass().getSimpleName(), "getAllParkingCompanyLEOs()",
				"Ã�nside the class method getAllParkingCompanyLEOs");
		AllUsersResponseDTO response = new AllUsersResponseDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSystemExists(enterpriseSystemId, session)) {
			throw new InvalidInputException("Parking Company doesn't exists with id: " + enterpriseSystemId);
		}
		List<UserAdditionResponseDTO> pcUsers = new ArrayList<UserAdditionResponseDTO>();
		List<EnterpriseSystemUser> users = null;
		try {
			response.setEnterpriseSystemId(enterpriseSystemId);
			Criteria criteria = session.createCriteria(EnterpriseSystemUser.class);
			criteria.add(Restrictions.eq("eSystem.eSystemId", enterpriseSystemId));
			criteria.add(Restrictions.eq("role", "LawEnforcementOfficer"));
			if ((offset == null) && (limit == null)) {

			} else {
				criteria.setFirstResult(offset);
				criteria.setMaxResults(limit);
			}
			users = (List<EnterpriseSystemUser>) criteria.list();

			Integer size = users.size();
			logger.debug(this.getClass().getSimpleName(), "getAllParkingCompanyLEOs()", "size of users list: " + size);
			if (!users.isEmpty()) {
				pcUsers = convertToUsersAdditionResponseDTO(users);
				response.setUserResponse(pcUsers);
			}
			response.setTotalRecords(size);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getAllParkingCompanyLEOs()", "exception :" + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public AllEnterpriseSiteUsersDTO getAllEnterpriseSiteUsers(String enterpriseSiteId, String enterpriseSystemId)
			throws InvalidInputException {
		AllEnterpriseSiteUsersDTO response = new AllEnterpriseSiteUsersDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSystemExists(enterpriseSystemId, session)) {
			throw new InvalidInputException("Enterprise System doesn't exists with id: " + enterpriseSystemId);
		}
		if (!isEnterpriseSiteExists(enterpriseSiteId, session)) {
			throw new InvalidInputException("Enterprise Site doesn't exists with id: " + enterpriseSiteId);
		}
		try {
			EnterpriseSite enterpriseSite = (EnterpriseSite) session.get(EnterpriseSite.class, enterpriseSiteId);
			if (enterpriseSite != null) {
				List<EnterpriseSystemUser> allEnterpriseSiteUsers = enterpriseSite.getUsers();
				allEnterpriseSiteUsers = new ArrayList(new LinkedHashSet(allEnterpriseSiteUsers));
				if (allEnterpriseSiteUsers != null) {
					List<UserAdditionResponseDTO> userResponse = convertToUsersAdditionResponseDTO(
							allEnterpriseSiteUsers);
					response.setUserResponse(userResponse);
					response.setEnterpriseSiteId(enterpriseSiteId);
					response.setEnterpriseSystemId(enterpriseSystemId);
				}
			}
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
		return response;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private List<EnterpriseSystemUser> getListOfParkingSpaceUsers(String enterpriseSystemId, String enterpriseSiteId,
			Integer offset, Integer limit, Session session) {

		List<EnterpriseSystemUser> users = null;
		try {
			Criteria criteria = session.createCriteria(EnterpriseSystemUser.class);
			criteria.add(Restrictions.eq("eSystem.eSystemId", enterpriseSystemId));
			criteria.add(Restrictions.ne("role", "PCADMIN"));
			criteria.add(Restrictions.eq("eSystem.eSites.eSiteId", enterpriseSiteId));
			if ((offset == null) && (limit == null)) {

			} else {
				criteria.setFirstResult(offset);
				criteria.setMaxResults(limit);
			}
			users = (List<EnterpriseSystemUser>) criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public UserAssigmentResponseDTO allotEnterpriseSiteToUser(String systemId, UserAssigmentRequestDTO dto)
			throws InvalidInputException {
		logger.debug(this.getClass().getSimpleName(), "allotParkingSpaceToUser()", "dto=" + dto);

		UserAssigmentResponseDTO response = new UserAssigmentResponseDTO();
		boolean result = false;
		switch (dto.getOperationType().toUpperCase()) {
		case "assign":
		case "ASSIGN":
			result = allocateParkingSpaceToUser("ASSIGN", systemId, dto.getEnterpriseSiteId(), dto.getUserId(),
					dto.getUserRequestId());
			logger.debug(this.getClass().getSimpleName(), "allotParkingSpaceToUser()", "result=" + result);

			response = convertUserAssignmentResponse(dto, result);
			logger.debug(this.getClass().getSimpleName(), "allotParkingSpaceToUser()", "response=" + response);

			break;
		case "deassign":
		case "DEASSIGN":
			result = allocateParkingSpaceToUser("DEASSIGN", systemId, dto.getEnterpriseSiteId(), dto.getUserId(),
					dto.getUserRequestId());
			response = convertUserAssignmentResponse(dto, result);
			break;
		}
		return response;
	}

	private UserAssigmentResponseDTO convertUserAssignmentResponse(UserAssigmentRequestDTO dto, boolean result) {
		UserAssigmentResponseDTO response = new UserAssigmentResponseDTO();
		if (result) {
			response.setResponse("SUCCESS");
		} else {
			response.setResponse("FAILURE");
		}
		response.setOperationType(dto.getOperationType());
		response.setEnterpriseSiteId(dto.getEnterpriseSiteId());
		response.setUserId(dto.getUserId());
		return response;
	}

	private boolean allocateParkingSpaceToUser(String type, String SystemId, String enterpriseSiteid, String userId,
			String requestId) throws InvalidInputException {
		logger.debug(this.getClass().getSimpleName(), "allotParkingSpaceToUser()", "userId=" + userId);

		boolean response = false;
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		UserRequest userRequest = null;
		if (!isEnterpriseSiteExists(enterpriseSiteid, session)) {
			throw new InvalidInputException("Enterprise Site doesn't exists with id: " + enterpriseSiteid);
		}
		if (!isEnterpriseSystemExists(SystemId, session)) {
			throw new InvalidInputException("Enterprise System doesn't exists with id: " + SystemId);
		}
		try {
			session.beginTransaction();
			EnterpriseSite eSite = (EnterpriseSite) session.get(EnterpriseSite.class, enterpriseSiteid);
			EnterpriseSystemUser user = (EnterpriseSystemUser) session.get(EnterpriseSystemUser.class, userId);
			logger.debug(this.getClass().getSimpleName(), "allotParkingSpaceToUser()",
					"user = " + user + " pSpace = " + eSite + " type = " + type);
			if (user != null && eSite != null) {
				if (type.equalsIgnoreCase("ASSIGN")) {
					user.addEnterpriseSite(eSite);
					eSite.addUser(user);
				} else if (type.equalsIgnoreCase("DEASSIGN")) {
					user.removeEnterpriseSite(eSite);
					eSite.removeUser(user);
					if (user.getRole().equalsIgnoreCase("ASST MANAGER")) {
						assignDefaultRoles(session, user);
					}
				}
				// session.save(user);
				if (requestId != null) {
					userRequest = (UserRequest) session.get(UserRequest.class, requestId);
				}
				if (requestId == null) {
					userRequest = new UserRequest();
					userRequest.setCreatedTime(getCurrentTimeStamp());
					userRequest.setOperationType(type);
					userRequest.setOperatedUserId(userId);
					EnterpriseSystemUser admin = (EnterpriseSystemUser) session.get(EnterpriseSystemUser.class,
							"USR00000001");
					userRequest.setEnterpriseSystemUser(admin);
				}
				userRequest.setRequestState("COMPLETED");
				userRequest.setCompletedTime(getCurrentTimeStamp());
				userRequest.setParkingSpace(eSite);
				session.save(userRequest);
				session.getTransaction().commit();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getSimpleName(), "allotParkingSpaceToUser()", "Exception = " + e.getMessage());

			session.getTransaction().rollback();
		} finally {
			session.flush();
			session.close();
		}
		logger.debug(this.getClass().getSimpleName(), "allotParkingSpaceToUser()", "response = " + response);

		return response;

	}

	@SuppressWarnings("unchecked")
	private void assignDefaultRoles(Session session, EnterpriseSystemUser user) {
		List<Operations> operations = null;
		try {
			// set defaults Role Operations
			Roles asstManager = (Roles) session.get(Roles.class, "ROLE00000003");
			operations = asstManager.getOperations();
			operations = new ArrayList(new HashSet(operations));
			int size = operations.size();
			if (size > 0) {
				List<String> operationsPermitted = getMatchingOperations(convertToOperationsDTO(operations), session);
				operationsPermitted = new ArrayList(new HashSet(operationsPermitted));
				user.setPermittedOperations(operationsPermitted);
			}
			session.saveOrUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<OperationsDTO> convertToOperationsDTO(List<Operations> operations) {
		List<OperationsDTO> operationsListDTO = new ArrayList<>();
		for (Operations operation : operations) {
			OperationsDTO dto = new OperationsDTO();
			dto.setExecutePermission(operation.getExecutePermission());
			dto.setOperationId(operation.getOperationId());
			dto.setModuleOperationName(operation.getModuleOperationName());
			dto.setReadPermission(operation.getReadPermission());
			dto.setWritePermission(operation.getWritePermission());
			operationsListDTO.add(dto);
		}
		return operationsListDTO;
	}

	private Operations getMatchingOperations(String moduleOperationName, Boolean executePermission,
			Boolean readPermission, Boolean writePermission, Session session) {
		try {
			Criteria cr = session.createCriteria(Operations.class);
			cr.add(Restrictions.eq("moduleOperationName", moduleOperationName));
			cr.add(Restrictions.eq("executePermission", executePermission));
			cr.add(Restrictions.eq("writePermission", writePermission));
			cr.add(Restrictions.eq("readPermission", readPermission));
			Operations operation = (Operations) cr.uniqueResult();
			if (operation != null) {
				return operation;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	private List<String> getMatchingOperations(List<OperationsDTO> allowedPermissions, Session session) {
		List<String> response = new ArrayList<String>();
		for (OperationsDTO operation : allowedPermissions) {
			Operations operationId = getMatchingOperations(operation.getModuleOperationName(),
					operation.getExecutePermission(), operation.getReadPermission(), operation.getWritePermission(),
					session);
			if (operationId != null) {
				response.add(operationId.getOperationId());
			}
		}
		return response;
	}

	@Override
	public EnterpriseSitesListResponseDTO getListofEnterpriseSites(String enterpriseSystemId)
			throws InvalidInputException {
		EnterpriseSitesListResponseDTO response = new EnterpriseSitesListResponseDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		List<EnterpriseSystemLEODTO> leosWithPS = new ArrayList<EnterpriseSystemLEODTO>();
		if (!isEnterpriseSystemExists(enterpriseSystemId, session)) {
			throw new InvalidInputException("Enterprise System doesn't exists with id: " + enterpriseSystemId);
		}
		try {
			EnterpriseSystem parkingCompany = (EnterpriseSystem) session.get(EnterpriseSystem.class,
					enterpriseSystemId);
			if (parkingCompany != null) {
				List<EnterpriseSite> parkingSpaceList = parkingCompany.geteSites();
				leosWithPS = convertParkingSpacesListResponseDTO(parkingSpaceList);
				if (leosWithPS != null) {
					response.setEnterpriseSitesList(leosWithPS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	private List<EnterpriseSystemLEODTO> convertParkingSpacesListResponseDTO(List<EnterpriseSite> eSites) {
		List<EnterpriseSystemLEODTO> parkingSpaceLeos = new ArrayList<EnterpriseSystemLEODTO>();
		for (EnterpriseSite eSite : eSites) {
			EnterpriseSystemLEODTO leoPS = new EnterpriseSystemLEODTO();
			leoPS.setEnterpriseSystemId(eSite.geteSiteId());
			leoPS.setLeoAssigned(isLEOExists(eSite.getUsers()));
			parkingSpaceLeos.add(leoPS);
		}
		return parkingSpaceLeos;
	}

	private boolean isLEOExists(List<EnterpriseSystemUser> users) {
		boolean response = false;
		for (EnterpriseSystemUser user : users) {
			if (user.getRole().equalsIgnoreCase("LAW ENFORCEMENT OFFICER")) {
				return true;
			}
		}
		return response;
	}

	@Override
	@Transactional
	public UserAdditionResponseDTO createUserWithUserRequest(UserAdditionRequestDTO requestDTO,
			String enterpriseSystemId, String enterpriseSiteId, String userRequestId, String adminId)
					throws UserAlreadyAvailableException, InvalidInputException {
		logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
				"Inside the User Creation Method!!");
		UserAdditionResponseDTO response = new UserAdditionResponseDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		session.beginTransaction();
		if (!isAdmin(adminId, session)) {
			throw new InvalidInputException("Invalid Admin Id:  " + adminId);
		}
		if (!isEnterpriseSiteExists(enterpriseSiteId, session)) {
			throw new InvalidInputException("Invalid enterprise site Id:  " + enterpriseSiteId);
		}
		if (!isEnterpriseSystemExists(enterpriseSystemId, session)) {
			throw new InvalidInputException("Invalid enterprise system Id:  " + enterpriseSystemId);
		}

		User user = getUserByUserName(requestDTO.getUserName(), session);
		if (user != null) {
			throw new UserAlreadyAvailableException("UserName already Exists");
		}
		if (!isRequestExists(userRequestId, session)) {
			throw new UserAlreadyAvailableException(" Invalid User Request already Exists");
		}
		try {
			EnterpriseSystemUser pcUser = new EnterpriseSystemUser();
			pcUser.setActive(requestDTO.isActive());
			pcUser.setCreated(getCurrentTimeStamp());
			pcUser.setDisplayName(requestDTO.getDisplayName());
			List<Email> emails = getEmails(requestDTO.getEmails());
			List<PhoneNumber> phone = getPhoneNumbers(requestDTO.getPhoneNumbers());
			List<Photo> pics = getPics(requestDTO.getPics());
			pcUser.setEmails(emails);
			pcUser.setPhoneNumbers(phone);
			pcUser.setPics(pics);
			pcUser.setLastModifiedTime(getCurrentTimeStamp());
			pcUser.setFamilyName(requestDTO.getFamilyName());
			pcUser.setFormattedName(requestDTO.getFormattedName());
			pcUser.setGivenName(requestDTO.getGivenName());
			pcUser.setHonorificPrifix(requestDTO.getHonorificPrifix());
			pcUser.setMiddleName(requestDTO.getMiddleName());
			pcUser.setNickName(requestDTO.getNickName());
			pcUser.setPassword(requestDTO.getPassword());
			pcUser.setUserName(requestDTO.getUserName());

			if (requestDTO.getRole() != null) {
				Roles role = getRole(session, requestDTO.getRole());
				logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()", role.getRoleName());
				pcUser.setRoles(role);
				List<Operations> operations = role.getOperations();
				logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
						" Size of Operations: " + operations.size());
				for (Operations operation : operations) {
					logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
							" Operations ID add:::  " + operation.getOperationId());
					pcUser.addPermittedOperations(operation.getOperationId());
				}
			}

			EnterpriseSystem eSystem = getEnterpriseSystemDetails(session, enterpriseSystemId);
			pcUser.seteSystem(eSystem);
			pcUser.setRole(requestDTO.getRole());

			EnterpriseSite eSite = getEnterpriseSiteDetails(session, enterpriseSiteId);
			pcUser.addEnterpriseSite(eSite);

			eSite.addUser(pcUser);

			session.save(pcUser);
			// Updating user request
			UserRequest userRequest = (UserRequest) session.get(UserRequest.class, userRequestId);
			userRequest.setOperatedUserId(pcUser.getUserId());
			userRequest.setRequestState("COMPLETED");
			session.save(userRequest);
			logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
					"UserID  :" + pcUser.getUserId());
			session.getTransaction().commit();
			response = convertToUserAdditionResponse(pcUser.getUserId(), requestDTO);
			logger.debug(this.getClass().getSimpleName(), "createUserWithUserRequest()",
					"UserID 2 :" + pcUser.getUserId());

		} catch (Exception e) {
			session.getTransaction().rollback();
			logger.error(this.getClass().getSimpleName(), "createUserWithUserRequest()",
					"Exception occured in the User Creation!!!");
			e.printStackTrace();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	@Override
	public AllAsstManagersResponseDTO getAllEnterpriseSiteAsstManagers(String enterpriseSystemId, Integer offset,
			Integer limit) throws InvalidInputException {
		logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteAsstManagers()",
				"Ã�nside the class method getAllParkingCompanyLEOs");
		AllAsstManagersResponseDTO response = new AllAsstManagersResponseDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		List<UserAdditionResponseDTO> pcUsers = new ArrayList<UserAdditionResponseDTO>();
		List<EnterpriseSystemUser> users = null;
		if (!isEnterpriseSystemExists(enterpriseSystemId, session)) {
			throw new InvalidInputException("Enterprise System doesn't exists with id: " + enterpriseSystemId);
		}
		try {
			response.setEnterpriseSystemId(enterpriseSystemId);
			Criteria criteria = session.createCriteria(EnterpriseSystemUser.class);
			criteria.add(Restrictions.eq("eSystem.eSystemId", enterpriseSystemId));
			criteria.add(Restrictions.eq("role", "AsstManager"));
			if ((offset == null) && (limit == null)) {

			} else {
				criteria.setFirstResult(offset);
				criteria.setMaxResults(limit);
			}
			users = (List<EnterpriseSystemUser>) criteria.list();
			users = getUnAssingedAsstManagers(users);
			if (!users.isEmpty()) {
				pcUsers = convertToUsersAdditionResponseDTO(users);
				response.setUserResponse(pcUsers);
			}
			Integer size = users.size();
			logger.debug(this.getClass().getSimpleName(), "getAllEnterpriseSiteAsstManagers()",
					"size of users list: " + size);
			response.setTotalRecords(size);
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "getAllEnterpriseSiteAsstManagers()",
					"exception :" + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	private List<EnterpriseSystemUser> getUnAssingedAsstManagers(List<EnterpriseSystemUser> users) {
		List<EnterpriseSystemUser> unassignedUser = new ArrayList<EnterpriseSystemUser>();
		for (EnterpriseSystemUser user : users) {
			if (user.getEnterpriseSites().size() == 0) {
				unassignedUser.add(user);
			}
		}
		return unassignedUser;
	}

	private List<AsstManagerDTO> convertAsstManagerResponse(List asstManagerList) {
		List<AsstManagerDTO> asstManagerData = new ArrayList<AsstManagerDTO>();
		for (Object object : asstManagerList) {
			Map row = (Map) object;
			AsstManagerDTO asstManagers = new AsstManagerDTO();
			asstManagers.setDisplayName((String) row.get("displayName"));
			asstManagers.setUserName((String) row.get("userName"));
			asstManagers.setUserId((String) row.get("userId"));
			@SuppressWarnings("unchecked")
			List<EmailDTO> email = (List<EmailDTO>) row.get("emails");
			@SuppressWarnings("unchecked")
			List<PhoneNumberDTO> phone = (List<PhoneNumberDTO>) row.get("phoneNumbers");
			asstManagers.setEmails(email);
			asstManagers.setPhoneNumbers(phone);
			System.out.println(asstManagers.toString());
			asstManagerData.add(asstManagers);
		}
		return asstManagerData;
	}

	private List<UserAdditionResponseDTO> convertToUserAdditionResponseDTO(List<User> managers) {
		logger.debug(this.getClass().getSimpleName(), "convertToUsersAdditionResponseDTO()",
				"Inside the method convertToUsersAdditionResponseDTO()");
		List<UserAdditionResponseDTO> usersList = new ArrayList<>();
		if (managers != null) {
			for (User user : managers) {
				UserAdditionResponseDTO userResponse = new UserAdditionResponseDTO();
				userResponse.setActive(user.isActive());
				userResponse.setCreated(getStringFormatTimeStamp(user.getCreated()));
				userResponse.setDisplayName(user.getDisplayName());
				List<EmailDTO> emails = getEmailDTO(user.getEmails());
				List<PhoneNumberDTO> phoneNumbers = getPhoneNumberDTO(user.getPhoneNumbers());
				List<PhotoDTO> pics = getPhotoDTO(user.getPics());
				userResponse.setEmails(emails);
				userResponse.setFamilyName(user.getFamilyName());
				userResponse.setFormattedName(user.getFormattedName());
				userResponse.setGivenName(user.getGivenName());
				userResponse.setHonorificPrifix(user.getHonorificPrifix());
				userResponse.setLastModifiedTime(getStringFormatTimeStamp(user.getLastModifiedTime()));
				userResponse.setMiddleName(user.getMiddleName());
				userResponse.setNickName(user.getNickName());
				userResponse.setPassword("");
				userResponse.setPhoneNumbers(phoneNumbers);
				userResponse.setPics(pics);
				userResponse.setRole(user.getRole());
				userResponse.setUserId(user.getUserId());
				userResponse.setUserName(user.getUserName());
				usersList.add(userResponse);
			}
		}
		logger.debug(this.getClass().getSimpleName(), "convertToUsersAdditionResponseDTO()",
				"end of the method convertToUsersAdditionResponseDTO()");
		return usersList;

	}

	@Override
	@Transactional
	public UserDeletionResponseDTO deleteUserWithRequestId(UserDeletionRequestDTO dto, String userRequestId)
			throws UserNotFoundException {
		logger.debug(this.getClass().getSimpleName(), "deleteUserWithRequestId()", "Inside the User Deletion Method!!");
		UserDeletionResponseDTO response = new UserDeletionResponseDTO();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		session.beginTransaction();
		User user = null;
		user = getUser(dto.getUserId(), session);
		if (user == null) {
			logger.debug(this.getClass().getSimpleName(), "deleteUserWithRequestId()", "UserID  :" + user.getUserId());
			throw new UserNotFoundException("User ID does not exists in the Database");
		}
		try {
			if (isAdmin(dto.getAdminUserId(), session)) {
				if (dto.getEnterpriseSystemId() != null && dto.getUserId() != null) {
					user = getUser(dto.getUserId(), session);
					if (user != null) {
						String userId = user.getUserId();
						logger.debug(this.getClass().getSimpleName(), "deleteUserWithRequestId()",
								"UserID  :" + user.getUserId());
						session.delete(user);
						UserRequest userRequest = (UserRequest) session.get(UserRequest.class, userRequestId);
						userRequest.setRequestState("COMPLETED");
						session.save(userRequest);
						session.getTransaction().commit();
						response.setResponse("User Deletion was Succesful!!!");
						response.setAdminUserId(dto.getAdminUserId());
						response.setEnterpriseSystemId(dto.getEnterpriseSystemId());
						response.setUserId(userId);
					}
				}
			}
		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName(), "deleteUser()", "Exception occured in the User Deletion!!!");
			e.printStackTrace();
			session.getTransaction().commit();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	@Override
	@Transactional
	public EnterpriseSitesListDTO getEnterpriseSitesAssignedtoUserId(String enterpriseSystemId, String userId)
			throws InvalidInputException, UserNotFoundException {
		EnterpriseSitesListDTO response = new EnterpriseSitesListDTO();

		List<EnterpriseSite> entperpriseSites = new ArrayList<EnterpriseSite>();
		Session session = enterpriseSystemInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSystemExists(enterpriseSystemId, session)) {
			throw new InvalidInputException("Enterprise System doesn't exists with id: " + enterpriseSystemId);
		}
		User user1 = null;
		user1 = getUser(userId, session);
		if (user1 == null || StringUtils.isBlank(user1.getDisplayName())) {
			logger.debug(this.getClass().getSimpleName(), "getEnterpriseSitesAssignedtoUserId()",
					"UserID  :" + user1.getUserId());
			throw new InvalidInputException("User ID does not exists in the Database");
		}
		try {
			EnterpriseSystemUser user = (EnterpriseSystemUser) session.get(EnterpriseSystemUser.class, userId);
			entperpriseSites = user.getEnterpriseSites();
			if (entperpriseSites != null) {
				List<String> pSpaces = convertToEnterpriseSiteList(entperpriseSites);
				if (pSpaces != null) {
					response = new EnterpriseSitesListDTO();
					response.setEnterpriseSitesList(new LinkedHashSet(pSpaces));
				}
			} else {
				response = new EnterpriseSitesListDTO();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	private List<String> convertToEnterpriseSiteList(List<EnterpriseSite> enterpriseSites) {
		List<String> list = new ArrayList<String>();
		for (EnterpriseSite eSite : enterpriseSites) {
			list.add(eSite.geteSiteId());
		}
		return list;
	}

	private boolean isRequestExists(String requestId, Session session) {
		if (requestId != null) {
			UserRequest parkingSpace = (UserRequest) session.get(UserRequest.class, requestId);
			if (parkingSpace.getRequestId() != null) {
				return true;
			}
		}
		return false;
	}

}

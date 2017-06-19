package com.java4u.eum.esite.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.java4u.commons.exceptions.InvalidInputException;
import com.java4u.commons.exceptions.UserAlreadyAvailableException;
import com.java4u.commons.exceptions.UserNotFoundException;
import com.java4u.logger.App_logger;
import com.java4u.logger.EModuleName;
import com.java4u.logger.LoggerFactory;
import com.java4u.eum.entities.Email;
import com.java4u.eum.entities.Operations;
import com.java4u.eum.entities.EnterpriseSystem;
import com.java4u.eum.entities.EnterpriseSystemUser;
import com.java4u.eum.entities.EnterpriseSite;
import com.java4u.eum.entities.PhoneNumber;
import com.java4u.eum.entities.Photo;
import com.java4u.eum.entities.Roles;
import com.java4u.eum.entities.User;
import com.java4u.eum.entities.UserRequest;
import com.java4u.eum.util.EnterpriseSystemInputValidator;
import com.java4u.eum.esite.dao.UserManagementDAO;
import com.java4u.eum.esite.dto.AllUsersResponseDTO;
import com.java4u.eum.esite.dto.OperationsDTO;
import com.java4u.eum.esite.dto.PhotoDTO;
import com.java4u.eum.esite.dto.UserAdditionRequestDTO;
import com.java4u.eum.esite.dto.UserAdditionResponseDTO;
import com.java4u.eum.esite.dto.UserDeletionRequestDTO;
import com.java4u.eum.esite.dto.UserDeletionResponseDTO;
import com.java4u.eum.esite.dto.UserRolesDTO;
import com.java4u.eum.esystem.dto.EmailDTO;
import com.java4u.eum.esystem.dto.PhoneNumberDTO;

public class UserManagementDaoImpl implements UserManagementDAO {

	private static final App_logger logger = LoggerFactory.getLogger(EModuleName.CONTROLLER);

	private EnterpriseSystemInputValidator enterpriseInputValidator;

	public void setEnterpriseInputValidator(EnterpriseSystemInputValidator enterpriseInputValidator) {
		this.enterpriseInputValidator = enterpriseInputValidator;
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

	@SuppressWarnings("unchecked")
	private List<OperationsDTO> getListOfOperations() {
		Set<OperationsDTO> inputData = new LinkedHashSet<>();

		// creation of operation input data

		OperationsDTO sitesRead = new OperationsDTO("Sites", true, false, false);
		OperationsDTO sitesView = new OperationsDTO("Sites", false, true, false);
		OperationsDTO sitesFull = new OperationsDTO("Sites", false, false, true);

		inputData.add(sitesRead);
		inputData.add(sitesView);
		inputData.add(sitesFull);

		OperationsDTO siteStatusRead = new OperationsDTO("SiteStatus", true, false, false);
		OperationsDTO siteStatusView = new OperationsDTO("SiteStatus", false, true, false);
		OperationsDTO siteStatusFull = new OperationsDTO("SiteStatus", false, false, true);

		inputData.add(siteStatusRead);
		inputData.add(siteStatusView);
		inputData.add(siteStatusFull);

		OperationsDTO authenticationSourceRead = new OperationsDTO("AuthenticationSource", true, false, false);
		OperationsDTO authenticationSourceView = new OperationsDTO("AuthenticationSource", false, true, false);
		OperationsDTO authenticationSourceFull = new OperationsDTO("AuthenticationSource", false, false, true);

		inputData.add(authenticationSourceRead);
		inputData.add(authenticationSourceView);
		inputData.add(authenticationSourceFull);

		OperationsDTO siteAdvancedUsersRead = new OperationsDTO("SiteAdvancedUsers", true, false, false);
		OperationsDTO siteAdvancedUsersView = new OperationsDTO("SiteAdvancedUsers", false, true, false);
		OperationsDTO siteAdvancedUsersFull = new OperationsDTO("SiteAdvancedUsers", false, false, true);

		inputData.add(siteAdvancedUsersRead);
		inputData.add(siteAdvancedUsersView);
		inputData.add(siteAdvancedUsersFull);

		OperationsDTO siteSchduledRead = new OperationsDTO("SiteSchduled", true, false, false);
		OperationsDTO siteSchduledView = new OperationsDTO("SiteSchduled", false, true, false);
		OperationsDTO siteSchduledFull = new OperationsDTO("SiteSchduled", false, false, true);

		inputData.add(siteSchduledRead);
		inputData.add(siteSchduledView);
		inputData.add(siteSchduledFull);

		OperationsDTO siteUsersRead = new OperationsDTO("SiteUsers", true, false, false);
		OperationsDTO siteUsersView = new OperationsDTO("SiteUsers", false, true, false);
		OperationsDTO siteUsersFull = new OperationsDTO("SiteUsers", false, false, true);

		inputData.add(siteUsersRead);
		inputData.add(siteUsersView);
		inputData.add(siteUsersFull);

		OperationsDTO zonesRead = new OperationsDTO("Zones", true, false, false);
		OperationsDTO zonesView = new OperationsDTO("Zones", false, true, false);
		OperationsDTO zonesFull = new OperationsDTO("Zones", false, false, true);

		inputData.add(zonesRead);
		inputData.add(zonesView);
		inputData.add(zonesFull);

		OperationsDTO placesRead = new OperationsDTO("Places", true, false, false);
		OperationsDTO placesView = new OperationsDTO("Places", false, true, false);
		OperationsDTO placesFull = new OperationsDTO("Places", false, false, true);

		inputData.add(placesRead);
		inputData.add(placesView);
		inputData.add(placesFull);

		OperationsDTO pinsRead = new OperationsDTO("PINs", true, false, false);
		OperationsDTO pinsView = new OperationsDTO("PINs", false, true, false);
		OperationsDTO pinsFull = new OperationsDTO("PINs", false, false, true);

		inputData.add(pinsRead);
		inputData.add(pinsView);
		inputData.add(pinsFull);

		OperationsDTO schedulesRead = new OperationsDTO("Schedules", true, false, false);
		OperationsDTO schedulesView = new OperationsDTO("Schedules", false, true, false);
		OperationsDTO schedulesFull = new OperationsDTO("Schedules", false, false, true);

		inputData.add(schedulesRead);
		inputData.add(schedulesView);
		inputData.add(schedulesFull);

		OperationsDTO overrideRead = new OperationsDTO("Override", true, false, false);
		OperationsDTO overrideView = new OperationsDTO("Override", false, true, false);
		OperationsDTO overrideFull = new OperationsDTO("Override", false, false, true);

		inputData.add(overrideRead);
		inputData.add(overrideView);
		inputData.add(overrideFull);

		OperationsDTO superAdminsRead = new OperationsDTO("SuperAdmins", true, false, false);
		OperationsDTO superAdminsView = new OperationsDTO("SuperAdmins", false, true, false);
		OperationsDTO superAdminsFull = new OperationsDTO("SuperAdmins", false, false, true);

		inputData.add(superAdminsRead);
		inputData.add(superAdminsView);
		inputData.add(superAdminsFull);

		OperationsDTO siteSuperAdminsRead = new OperationsDTO("SiteSuperAdmins", true, false, false);
		OperationsDTO siteSuperAdminsView = new OperationsDTO("SiteSuperAdmins", false, true, false);
		OperationsDTO siteSuperAdminsFull = new OperationsDTO("SiteSuperAdmins", false, false, true);

		inputData.add(siteSuperAdminsRead);
		inputData.add(siteSuperAdminsView);
		inputData.add(siteSuperAdminsFull);

		inputData.add(zonesRead);
		inputData.add(zonesView);
		inputData.add(zonesFull);

		return new ArrayList(inputData);
	}

	private void printOperationsList(List<Operations> data) {
		for (Operations operation : data) {
			System.out.println(operation.getOperationId());
		}
	}

	private List<Roles> insertRoles(Session session) {
		List<Roles> roles = null;
		List<Operations> enterpriseSuperAdminOperations = getOperationsBasedOnRole("EnterpriseSuperAdminRoles",session);
		List<Operations> enterpriseFieldSupportAdminOperations = getOperationsBasedOnRole("EnterpriseFieldSupportAdminRoles", session);
		List<Operations> siteSuperAdminOperations = getOperationsBasedOnRole("SiteSuperAdminRoles", session);
		List<Operations> siteAdvancedUserOperations = getOperationsBasedOnRole("SiteAdvancedUserRoles", session);
		List<Operations> siteScheduleUserOperations = getOperationsBasedOnRole("SiteScheduleUserRoles", session);
		List<Operations> siteUserOperations = getOperationsBasedOnRole("SiteUserRoles", session);
		List<Operations> managerOperations = getOperationsBasedOnRole("ManagerRoles", session);
		List<Operations> asstManagerOperations = getOperationsBasedOnRole("AsstManagerRoles", session);
		List<Operations> leoOperations = getOperationsBasedOnRole("LEORoles", session);
		System.out.println("Operator Roles");

		try {
			session.beginTransaction();

			// Enterprise Super Admin Roles
			Roles enterpriseSuperAdminRole = new Roles();
			enterpriseSuperAdminRole.setRoleName("EnterpriseSuperAdmin");
			enterpriseSuperAdminRole.setOperations(enterpriseSuperAdminOperations);
			session.save(enterpriseSuperAdminRole);
			for (Operations operation : enterpriseFieldSupportAdminOperations) {
				operation.addRole(enterpriseSuperAdminRole);
				session.save(operation);
			}

			// Enterprise Field Support Admin Roles
			Roles enterpriseFieldSupportAdminRole = new Roles();
			enterpriseFieldSupportAdminRole.setRoleName("EnterpriseFieldSupportAdmin");
			enterpriseFieldSupportAdminRole.setOperations(enterpriseFieldSupportAdminOperations);
			session.save(enterpriseFieldSupportAdminRole);
			for (Operations operation : enterpriseFieldSupportAdminOperations) {
				operation.addRole(enterpriseFieldSupportAdminRole);
				session.save(operation);
			}

			// Site Super Admin Operations
			Roles siteSuperAdminRole = new Roles();
			siteSuperAdminRole.setRoleName("SiteSuperAdmin");
			siteSuperAdminRole.setOperations(siteSuperAdminOperations);
			session.save(siteSuperAdminRole);
			for (Operations operation : siteSuperAdminOperations) {
				operation.addRole(siteSuperAdminRole);
				session.save(operation);
			}

			// Site Advanced User Admin Operations
			Roles siteAdvancedUserRole = new Roles();
			siteAdvancedUserRole.setRoleName("SiteAdvancedUser");
			siteAdvancedUserRole.setOperations(siteAdvancedUserOperations);
			session.save(siteAdvancedUserRole);
			for (Operations operation : siteAdvancedUserOperations) {
				operation.addRole(siteAdvancedUserRole);
				session.save(operation);
			}

			// Site Scheduled User Operations
			Roles siteScheduleUserRole = new Roles();
			siteScheduleUserRole.setRoleName("SiteScheduleUser");
			siteScheduleUserRole.setOperations(siteScheduleUserOperations);
			session.save(siteScheduleUserRole);
			for (Operations operation : siteScheduleUserOperations) {
				operation.addRole(siteScheduleUserRole);
				session.save(operation);
			}

			// Site User Roles
			Roles siteUserRole = new Roles();
			siteUserRole.setRoleName("SiteUser");
			siteUserRole.setOperations(siteUserOperations);
			session.save(siteUserRole);
			for (Operations operation : siteUserOperations) {
				operation.addRole(siteUserRole);
				session.save(operation);
			}

			// Manager Roles
			Roles managerRole = new Roles();
			managerRole.setRoleName("Manager");
			managerRole.setOperations(managerOperations);
			session.save(managerRole);
			for (Operations operation : managerOperations) {
				operation.addRole(managerRole);
				session.save(operation);
			}

			// Asst Manager Roles
			Roles asstManageRole = new Roles();
			asstManageRole.setRoleName("AsstManager");
			asstManageRole.setOperations(asstManagerOperations);
			session.save(asstManageRole);
			for (Operations operation : asstManagerOperations) {
				operation.addRole(asstManageRole);
				session.save(operation);
			}

			// LEO Roles
			Roles leoRole = new Roles();
			leoRole.setRoleName("LawEnforcementOfficer");
			leoRole.setOperations(leoOperations);
			session.save(leoRole);
			for (Operations operation : leoOperations) {
				operation.addRole(leoRole);
				session.save(operation);
			}

			session.getTransaction().commit();
			roles = getRoelsFromDB(session);
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
		case "enterprisesuperadminroles":
		case "EnterpriseSuperAdminRoles":
			response = getAdminOperationsFromDB(session);
			break;
		case "enterprisefieldsupportadminRoles":
		case "EnterpriseFieldSupportAdminRoles":
			response = getAdminOperationsFromDB(session);
			break;
		case "sitesuperadminroles":
		case "SiteSuperAdminRoles":
			response = getAdminOperationsFromDB(session);
			break;
		case "siteadvanceduserroles":
		case "SiteAdvancedUserRoles":
			response = getAdminOperationsFromDB(session);
			break;
		case "sitescheduleuserroles":
		case "SiteScheduleUserRoles":
			response = getAdminOperationsFromDB(session);
			break;

		case "siteuserroles":
		case "SiteUserRoles":
			response = getAdminOperationsFromDB(session);
			break;

		case "managerroles":
		case "ManagerRoles":
			response = getAdminOperationsFromDB(session);
			break;

		case "asstmanagerroles":
		case "AsstManagerRoles":
			response = getAdminOperationsFromDB(session);
			break;

		case "leoroles":
		case "LEORoles":
			response = getAdminOperationsFromDB(session);
			break;
		}
		return response;
	}

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

	private List<Operations> getManagerOperationsFromDB(Session session) {
		List<Operations> operations = getAdminOperationsFromDB(session);
		operations.remove(8);
		Operations userManagement = getMatchingOperations("UserManagement", true, false, false, session);
		operations.add(userManagement);
		return operations;
	}

	@SuppressWarnings("unchecked")
	private List<Operations> getOperatorOperationsFromDB(Session session) {
		List<Operations> operations = null;
		session.beginTransaction();
		try {
			Criteria cr = session.createCriteria(Operations.class);
			cr.add(Restrictions.eq("readPermission", true));
			operations = (List<Operations>) cr.list();
			operations.remove(5);
			operations.remove(8);
			Operations userManagement = getMatchingOperations("UserManagement", true, false, false, session);
			operations.add(userManagement);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
		return operations;
	}

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

	@SuppressWarnings("unused")
	private List<Operations> getAdminOperations(Session session) {
		List<Operations> operationList = null;
		try {
			Criteria cr = session.createCriteria(Operations.class);
			operationList = (List<Operations>) cr.list();
			printOperationsList(operationList);
			return operationList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<Roles> getRoelsFromDB(Session session) {
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

	@Override
	@Transactional
	public void insertRolesandOperations() {
		logger.debug(this.getClass().getSimpleName(), "insertRolesandOperations()",
				"Inside the method--- insertRolesandOperations()");
		Session session = enterpriseInputValidator.getSessionFactory().openSession();
		try {
			List<Operations> operationList = getOperationsFromDB(session);
			System.out.println("Operation Size >>" + operationList.size());
			operationList.toString();
			List<Roles> roleList = getRoelsFromDB(session);
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

	private Timestamp getCurrentTimeStamp() {
		java.util.Date date = new java.util.Date();
		return new Timestamp(date.getTime());
	}

	@SuppressWarnings("unused")
	private Roles getRole(String roleName, Session session) {
		Roles role = null;
		session.beginTransaction();
		try {
			Criteria cr = session.createCriteria(Roles.class);
			cr.add(Restrictions.eq("roleName", roleName));
			List<Roles> results = (List<Roles>) cr.list();
			if (results.size() != 0 && results != null) {
				return (Roles) results.get(0);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
		return role;
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

	private List<Email> getEmails(List<EmailDTO> emailDto) {
		List<Email> response = new ArrayList<>();
		for (EmailDTO email : emailDto) {
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

	@Override
	public AllUsersResponseDTO getAllUsers(String enterpriseSiteId, String adminUserId) throws InvalidInputException {
		AllUsersResponseDTO response = null;
		List<EnterpriseSystemUser> users = new ArrayList<EnterpriseSystemUser>();
		Session session = enterpriseInputValidator.getSessionFactory().openSession();
		session.beginTransaction();
		if (!isEnterpriseSiteExists(enterpriseSiteId, session)) {
			throw new InvalidInputException("Enterprise Site doesnot exists with id :  " + enterpriseSiteId);
		}
		if (!isAdmin(adminUserId, session)) {
			throw new InvalidInputException("admin id is not valid : " + adminUserId);
		}
		try {
			users = getListOfEnterpriseSiteUsers(session, enterpriseSiteId);
			users = new ArrayList(new LinkedHashSet(users));
			if (users.size() > 0) {
				response = convertToAllUserResponseDTO(users, adminUserId, enterpriseSiteId, session);
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

	private List<UserAdditionResponseDTO> diffrenceInList(List<UserAdditionResponseDTO> users,
			List<String> psUserList) {
		List<UserAdditionResponseDTO> diffrenceList = new ArrayList<UserAdditionResponseDTO>();
		for (String str : psUserList) {
			for (UserAdditionResponseDTO user : users) {
				if (user.getUserId().equalsIgnoreCase(str)) {
					user.setRemarks("User in Deassignment Pending State");
					diffrenceList.add(user);
				} else {
					diffrenceList.add(user);
				}
			}
		}
		return diffrenceList;
	}

	private List<EnterpriseSystemUser> getListOfEnterpriseSiteUsers(Session session, String enterpriseSiteId) {
		List<EnterpriseSystemUser> users = null;
		try {
			Criteria cr = session.createCriteria(EnterpriseSite.class);
			cr.add(Restrictions.eq("eSiteId", enterpriseSiteId));
			EnterpriseSite eSite = (EnterpriseSite) cr.uniqueResult();
			users = eSite.getUsers();
			if (users.size() > 0) {
				// TODO : verify admin user is required to send in response or not
				//users.remove(0);
				return users;
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<String> getListOfEnterpriseSiteUsersWithDeassigned(Session session, String enterpriseSiteId) {
		List<String> response = new ArrayList<String>();
		Query query = session.createQuery(
				"from UserRequest ur where ur.enterpriseSite.eSiteId=:eSiteId and ur.requestState='Pending' "
						+ "and ur.operationType='DEASSIGN'");
		query.setParameter("eSiteId", enterpriseSiteId);
		List<UserRequest> requests = query.list();
		if (requests != null && !requests.isEmpty()) {
			for (UserRequest request : requests) {
				if (request.getOperatedUserId() != null && !request.getOperatedUserId().equalsIgnoreCase("")) {
					response.add(request.getOperatedUserId());
				}
			}
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private AllUsersResponseDTO convertToAllUserResponseDTO(List<EnterpriseSystemUser> users, String adminUserId,
			String enterpriseSiteId, Session session) {
		AllUsersResponseDTO dto = new AllUsersResponseDTO();
		dto.setAdminUserId(adminUserId);
		dto.setEnterpriseSiteId(enterpriseSiteId);
		List<UserAdditionResponseDTO> userResponse = null;
		try {
			List<String> pcUserList = getListOfEnterpriseSiteUsersWithDeassigned(session, enterpriseSiteId);
			userResponse = convertToUserAdditionResponse(users);
			if (pcUserList.size() > 0) {
				userResponse = diffrenceInList(userResponse, pcUserList);
			}
			List<UserAdditionResponseDTO> response = new ArrayList(new LinkedHashSet(userResponse));
			dto.setUserResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	private List<UserAdditionResponseDTO> convertToUserAdditionResponse(List<EnterpriseSystemUser> users) {
		List<UserAdditionResponseDTO> response = new ArrayList<>();
		for (EnterpriseSystemUser psUser : users) {
			UserAdditionResponseDTO userData = new UserAdditionResponseDTO();
			userData.setActive(psUser.isActive());
			String timeStamp = getStringFormatTimeStamp(psUser.getCreated());
			userData.setCreated(timeStamp);
			userData.setDisplayName(psUser.getDisplayName());
			List<EmailDTO> emails = getEmailDTO(psUser.getEmails());
			List<PhoneNumberDTO> phone = getPhoneNumberDTO(psUser.getPhoneNumbers());
			List<PhotoDTO> pics = getPhotoDTO(psUser.getPics());
			userData.setEmails(emails);
			userData.setPhoneNumbers(phone);
			userData.setPics(pics);
			userData.setFamilyName(psUser.getFamilyName());
			userData.setFormattedName(psUser.getFormattedName());
			userData.setGivenName(psUser.getGivenName());
			userData.setHonorificPrifix(psUser.getHonorificPrifix());
			String lastModifiedTime = getStringFormatTimeStamp(psUser.getLastModifiedTime());
			userData.setLastModifiedTime(lastModifiedTime);
			userData.setMiddleName(psUser.getMiddleName());
			userData.setNickName(psUser.getNickName());
			userData.setPassword("");
			userData.setRole(psUser.getRole());
			userData.setUserId(psUser.getUserId());
			userData.setUserName(psUser.getUserName());
			response.add(userData);
		}
		return response;
	}

	private String getStringFormatTimeStamp(Timestamp created) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").format(created);
		return timeStamp;
	}

	@Override
	public UserAdditionResponseDTO createUser(UserAdditionRequestDTO requestDTO, String enterpriseSiteId)
			throws UserAlreadyAvailableException, InvalidInputException {
		logger.debug(this.getClass().getSimpleName(), "createUser()", "Inside the User Creation Method!!");
		UserAdditionResponseDTO response = new UserAdditionResponseDTO();
		Session session = enterpriseInputValidator.getSessionFactory().openSession();
		session.beginTransaction();
		User user = getUserByUserName(requestDTO.getUserName(), session);
		if (user != null) {
			throw new UserAlreadyAvailableException("UserName already Exists");
		}
		if (!isEnterpriseSiteExists(enterpriseSiteId, session)) {
			throw new InvalidInputException("Enterprise Site doesnot exists with id :  " + enterpriseSiteId);
		}
		try {
			EnterpriseSystemUser psUser = new EnterpriseSystemUser();
			psUser.setActive(requestDTO.isActive());
			psUser.setCreated(getCurrentTimeStamp());
			psUser.setDisplayName(requestDTO.getDisplayName());
			List<Email> emails = getEmails(requestDTO.getEmails());
			List<PhoneNumber> phone = getPhoneNumbers(requestDTO.getPhoneNumbers());
			List<Photo> pics = getPics(requestDTO.getPics());
			psUser.setEmails(emails);
			psUser.setPhoneNumbers(phone);
			psUser.setPics(pics);
			psUser.setLastModifiedTime(getCurrentTimeStamp());
			psUser.setFamilyName(requestDTO.getFamilyName());
			psUser.setFormattedName(requestDTO.getFormattedName());
			psUser.setGivenName(requestDTO.getGivenName());
			psUser.setHonorificPrifix(requestDTO.getHonorificPrifix());
			psUser.setMiddleName(requestDTO.getMiddleName());
			psUser.setNickName(requestDTO.getNickName());
			psUser.setPassword(requestDTO.getPassword());
			psUser.setUserName(requestDTO.getUserName());

			if (requestDTO.getRole() != null) {
				Roles role = getRole(session, requestDTO.getRole());
				psUser.setRoles(role);
				List<Operations> operations = role.getOperations();
				// TODO: Fix need to be provided
				for (Operations operation : operations) {
					psUser.addPermittedOperations(operation.getOperationId());
				}
			}
			EnterpriseSite EnterpriseSite = getEnterpriseSiteDetails(session, enterpriseSiteId);

			psUser.addEnterpriseSite(EnterpriseSite);
			psUser.setRole(requestDTO.getRole());
			String userID = (String) session.save(psUser);
			System.out.println("UserID  :" + userID);
			logger.debug(this.getClass().getSimpleName(), "createUser()", "UserID  :" + psUser.getUserId());
			session.getTransaction().commit();
			response = convertToUserAdditionResponse(userID, requestDTO);
		} catch (Exception e) {
			session.getTransaction().rollback();
			logger.error(this.getClass().getSimpleName(), "createUser()", "Exception occured in the User Creation!!!");
			e.printStackTrace();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	private UserAdditionResponseDTO convertToUserAdditionResponse(String userID, UserAdditionRequestDTO psUser) {
		UserAdditionResponseDTO userData = new UserAdditionResponseDTO();
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userData;
	}

	private EnterpriseSite getEnterpriseSiteDetails(Session session, String enterpriseSiteId) {
		EnterpriseSite EnterpriseSite = new EnterpriseSite();
		try {
			Criteria cr = session.createCriteria(EnterpriseSite.class);
			cr.add(Restrictions.eq("eSiteId", enterpriseSiteId));
			List<EnterpriseSite> EnterpriseSiteList = cr.list();
			if (EnterpriseSiteList != null) {
				return (EnterpriseSite) EnterpriseSiteList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return EnterpriseSite;
	}

	private Roles getRole(Session session, String roleName) {
		try {
			Criteria cr = session.createCriteria(Roles.class);
			cr.add(Restrictions.eq("roleName", roleName));
			List<Roles> role = (List<Roles>) cr.list();
			if (role != null) {
				return role.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UserDeletionResponseDTO deleteUser(UserDeletionRequestDTO dto)
			throws UserNotFoundException, InvalidInputException {
		logger.debug(this.getClass().getSimpleName(), "deleteUser()", "Inside the User Deletion Method!!");
		UserDeletionResponseDTO response = new UserDeletionResponseDTO();
		Session session = enterpriseInputValidator.getSessionFactory().openSession();
		session.beginTransaction();
		User user = null;
		user = getUser(dto.getUserId(), session);
		if (user == null) {
			logger.debug(this.getClass().getSimpleName(), "deleteUser()", "UserID  :" + user.getUserId());
			throw new UserNotFoundException("User ID does not exists in the Database");
		}
		if (!isEnterpriseSiteExists(dto.getEnterpriseSiteId(), session)) {
			throw new InvalidInputException("Enterprise Site doesnot exists with id  " + dto.getEnterpriseSiteId());
		}

		try {
			if (isAdmin(dto.getAdminUserId(), session)) {
				if (dto.getEnterpriseSiteId() != null && dto.getUserId() != null) {
					user = getUser(dto.getUserId(), session);
					if (user != null && user instanceof EnterpriseSystemUser) {
						EnterpriseSystemUser pcUser = (EnterpriseSystemUser) user;
						List<EnterpriseSite> sites = pcUser.getEnterpriseSites();
						if(sites.size()>0 && sites!=null){
							for (int i = 0; i < sites.size(); i++) {
								sites.get(i).removeUser(pcUser);
							}
							if(pcUser.geteSystem()!=null){
							pcUser.geteSystem().getUsers().remove(pcUser);
							}
						}

						String userId = user.getUserId();
						logger.debug(this.getClass().getSimpleName(), "deleteUser()", "UserID  :" + user.getUserId());
						session.delete(user);
						session.getTransaction().commit();
						response.setResponse("User Deletion was Succesful!!!");
						response.setAdminUserId(dto.getAdminUserId());
						response.setEnterpriseSiteId(dto.getEnterpriseSiteId());
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

	private boolean isAdmin(String adminUserId, Session session) {
		try {
			User user = getUser(adminUserId, session);
			if (user.getRole().equalsIgnoreCase("EnterpriseSuperAdmin")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserRolesDTO getRolesOfUser(String userId, String psId) throws InvalidInputException {
		logger.debug(this.getClass().getSimpleName(), "getRolesOfUser()", "userId = " + userId);

		List<OperationsDTO> operationsList = null;
		UserRolesDTO response = new UserRolesDTO();
		logger.debug(this.getClass().getSimpleName(), "getRolesOfUser()",
				"EnterpriseInputValidator = " + enterpriseInputValidator);

		Session session = enterpriseInputValidator.getSessionFactory().openSession();
		session.beginTransaction();
		if (!isUserExists(userId, session)) {
			logger.error(this.getClass().getSimpleName(), "getRolesOfUser()",
					"user doesnot exists with userId : = " + userId);
			throw new InvalidInputException("user doesnot exists with userId : " + userId);
		}
		if (!isEnterpriseSiteExists(psId, session)) {
			logger.error(this.getClass().getSimpleName(), "getRolesOfUser()",
					"Enterprise Site Id doesnot exists: = " + psId);
			throw new InvalidInputException("Enterprise Site Id doesnot exists: " + psId);
		}

		try {
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("userId", userId));
			User user = (User) cr.uniqueResult();
			logger.debug(this.getClass().getSimpleName(), "getRolesOfUser()", "user = " + user);

			if (user != null) {
				List<String> permittedOperationList = user.getPermittedOperations();
				permittedOperationList = new ArrayList(new LinkedHashSet(permittedOperationList));
				List<Operations> operations = getOperationsFromDB(session);
				operationsList = getUpdatedResponse(permittedOperationList, operations);
				response.setAllowedOperations(operationsList);
				response.setRoleId(user.getRoles().getRoleId());
				response.setRoleName(user.getRole());
				response.setUserId(userId);
			}
		} catch (Exception e) {
			logger.debug(this.getClass().getSimpleName(), "getRolesOfUser()", "Exception e = " + e.getMessage());
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return response;
	}

	@SuppressWarnings("unused")
	private boolean isUserExists(String userId, Session session) {
		User user = getUser(userId, session);
		{
			if (user != null) {
				if (user.getUserId().equalsIgnoreCase(userId)) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unused")
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

	@SuppressWarnings("unused")
	private boolean isEnterpriseSiteExists(String enterpriseSiteId, Session session) {
		if (enterpriseSiteId != null) {
			EnterpriseSite EnterpriseSite = (EnterpriseSite) session.get(EnterpriseSite.class, enterpriseSiteId);
			if (EnterpriseSite.geteSiteId() != null) {
				return true;
			}
		}
		return false;
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

	@Override
	public List<String> getAllRoles(String enterpriseSiteId) {
		List<String> allRoles = new ArrayList<String>();
		Session session = enterpriseInputValidator.getSessionFactory().openSession();
		if (!isEnterpriseSiteExists(enterpriseSiteId, session)) {
			return null;
		}
		try {
			List<Roles> rolesList = getRoelsFromDB(session);
			for (Roles role : rolesList) {
				if (!role.getRoleName().equalsIgnoreCase("ADMIN")) {
					allRoles.add(role.getRoleName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return allRoles;
	}

	@Override
	public List<String> getAllOperations(String enterpriseSiteId) {
		List<String> allOperations = new ArrayList<String>();
		Session session = enterpriseInputValidator.getSessionFactory().openSession();

		if (!isEnterpriseSiteExists(enterpriseSiteId, session)) {
			return null;
		}

		List<Operations> operationsList = getOperationsUniqueListFromDB(session);
		for (Operations operation : operationsList) {
			allOperations.add(operation.getModuleOperationName());
		}
		return allOperations;
	}

	private List<Operations> getOperationsUniqueListFromDB(Session session) {
		List<Operations> operationList = new ArrayList<>();
		try {
			session.beginTransaction();
			Criteria cr = session.createCriteria(Operations.class);
			cr.add(Restrictions.eq("readPermission", true));
			operationList = (List<Operations>) cr.list();
			session.getTransaction().rollback();
		} catch (Exception e) {
			session.getTransaction().rollback();
			return null;
		} finally {
			session.flush();
			session.close();
		}
		return operationList;
	}

	@Override
	public List<OperationsDTO> addOrUpdatePermissions(String enterpriseSiteId,String userId, String adminUserId,
			List<OperationsDTO> allowedPermissions) throws InvalidInputException {

		List<OperationsDTO> operationsList = null;
		Session session = enterpriseInputValidator.getSessionFactory().openSession();
		session.beginTransaction();
		if(!isEnterpriseSiteExists(enterpriseSiteId, session)){
			throw new InvalidInputException("enterprise id does not exists with enterpriseSiteId : " + enterpriseSiteId);
		}
		if (!isUserExists(userId, session)) {
			throw new InvalidInputException("user doesnot exists with userId : " + userId);
		}
		if (!isAdmin(adminUserId, session)) {
			throw new InvalidInputException("admin id is not valid : " + adminUserId);
		}
		try {
			User user = getUser(userId, session);
			if (user != null) {
				user.getPermittedOperations();
				int size = allowedPermissions.size();
				if (size > 0) {
					List<String> operationsPermitted = getMatchingOperations(allowedPermissions, session);
					user.setPermittedOperations(operationsPermitted);
					session.saveOrUpdate(user);
				}
			}
			session.getTransaction().commit();
			User user1 = getUser(userId, session);
			if (user1 != null) {
				List<String> userList = user1.getPermittedOperations();
				List<Operations> operations = getOperationsFromDB(session);
				operationsList = getUpdatedResponse(userList, operations);
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.flush();
			session.close();
		}
		return operationsList;
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<OperationsDTO> getUpdatedResponse(List<String> permittedOperationList,
			List<Operations> operationsList) {
		List<OperationsDTO> responseList = new ArrayList<>();
		List<Operations> newList = new ArrayList<Operations>();
		for (String str : permittedOperationList) {
			int index = convertStringtoInteger(str);
			Operations operation = operationsList.get(index);
			newList.add(operation);
		}
		responseList = convertToOperationsDTO(newList);
		responseList = new ArrayList(new LinkedHashSet(responseList));
		return responseList;
	}

	private int convertStringtoInteger(String str) {
		str = str.replaceAll("\\D+", "");
		int i = Integer.parseInt(str);
		return i - 1;
	}

	@SuppressWarnings("unused")
	private List<String> convertOperationsString(List<OperationsDTO> allowedPermissions) {
		List<String> permittedList = new ArrayList<>();
		for (OperationsDTO dto : allowedPermissions) {
			permittedList.add(dto.getOperationId());
		}
		return permittedList;
	}

	@Override
	public boolean deleteSiteUser(String enterpriseSiteId) {

		return false;
	}

}

package com.altiux.eum.esystem.dao;

import java.util.List;

import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.eum.entities.Operations;
import com.altiux.eum.esystem.dto.EnterpriseSiteAdditionRequestDTO;
import com.altiux.eum.esystem.dto.EnterpriseSiteAdditionResponseDTO;
import com.altiux.eum.esystem.dto.EnterpriseSiteStatusRepsonse;
import com.altiux.eum.esystem.dto.EnterpriseSystemAdditionRequestDTO;
import com.altiux.eum.esystem.dto.EnterpriseSystemAdditionResponseDTO;
import com.altiux.eum.esystem.dto.EnterpriseSystemStatusRepsonse;
import com.altiux.eum.esystem.dto.OperationsDTO;
import com.altiux.eum.esystem.dto.OperationsInputResponseDTO;
import com.altiux.eum.esystem.dto.RolesDTO;
import com.altiux.eum.esystem.dto.SiteUserAdditionRequestDTO;
import com.altiux.eum.esystem.dto.SiteUserAdditionResponseDTO;

public interface EnterpriseSystemDAO {

	public EnterpriseSystemAdditionResponseDTO addEnterpriseSystem(EnterpriseSystemAdditionRequestDTO dto) throws InvalidInputException;

	public EnterpriseSiteAdditionResponseDTO addEnterpriseSite(String systemId, EnterpriseSiteAdditionRequestDTO dto)
			throws InvalidInputException;

	public EnterpriseSiteStatusRepsonse activeOrDectiveSite(String siteId, String status) throws InvalidInputException;

	public EnterpriseSystemStatusRepsonse activeOrDectiveSystem(String systemId, String status)
			throws InvalidInputException;

	public OperationsInputResponseDTO insertOperations(List<OperationsDTO> operations);

	public RolesDTO InsertRoles(RolesDTO dto);

	public EnterpriseSiteAdditionResponseDTO addEnterpriseSiteWithAdmin(String systemId,
			EnterpriseSiteAdditionRequestDTO dto) throws InvalidInputException;

	public SiteUserAdditionResponseDTO addSiteUser(String siteAdminId,String siteId, SiteUserAdditionRequestDTO dto) 
			throws InvalidInputException;
	
	public boolean deleteSiteUser(String siteAdminId, String siteUserId, String siteId) throws InvalidInputException;
}

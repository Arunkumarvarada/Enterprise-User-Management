/**
 * 
 */
package com.altiux.eum.esystem.dao;

import com.altiux.eum.esystem.dto.LoginControllerRequestDTO;
import com.altiux.eum.esystem.dto.LoginControllerResponseDTO;
import com.altiux.eum.esystem.dto.LoginWithRolesRequestDTO;
import com.altiux.eum.esystem.dto.LoginWithRolesResponseDTO;


public interface EnterpriseSystemLoginDAO {
	

	public LoginControllerResponseDTO login(LoginControllerRequestDTO requestDTO);
	
	public LoginWithRolesResponseDTO loginWithRolesResponse(LoginWithRolesRequestDTO requestDTO);
}

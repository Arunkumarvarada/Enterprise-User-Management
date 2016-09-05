package com.altiux.eum.esite.dao;

import com.altiux.commons.exceptions.InvalidInputException;
import com.altiux.commons.exceptions.EnterpriseSiteNotActiveException;
import com.altiux.commons.exceptions.UserNotFoundException;
import com.altiux.eum.esite.dto.LoginCredentialsDTO;
import com.altiux.eum.esite.dto.LoginSuccessDTO;

/**
 * This interface is useful to check the login credentials.It checks in the database in the user table.
 * 
 * @author Ashwini.s
 *
 */
public interface LoginDAO {

  /**
   * This api is useful to verify the user credentials during login.
   * 
   * @param requestDTO
   * @return an instance of type LoginSuccessDTO
   * @throws InvalidInputException 
 * @throws EnterpriseSiteNotActiveException 
 * @throws UserNotFoundException 
   */
  public LoginSuccessDTO login(LoginCredentialsDTO requestDTO) throws InvalidInputException, EnterpriseSiteNotActiveException, UserNotFoundException;
  
}

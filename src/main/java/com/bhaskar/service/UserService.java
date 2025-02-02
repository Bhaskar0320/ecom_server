package com.bhaskar.service;

import com.bhaskar.exception.UserException;
import com.bhaskar.model.User;

public interface UserService {

	 User findUserById(Long userId) throws UserException;
	
	 User findUserProfileByJwt(String jwt) throws UserException;
	
}

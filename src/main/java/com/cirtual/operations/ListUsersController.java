package com.cirtual.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cirtual.data.UserDao;
import com.cirtual.operations.service.TokenService;

@RestController
public class ListUsersController {
	@Autowired
	private UserDao userDAO;

	@Autowired
	private TokenService tokenService;

	// method will all messages from all users.
	@RequestMapping(method = RequestMethod.POST, value = "/list-all-users")
	public List<String> listAllUsers(@RequestParam(value = "tokenId") String tokenId) {
		int userId = -1;
		userId = tokenService.validateTokenId(tokenId);
		if( userId < 0){
			return null;
		}
		return userDAO.findAllUsers();
	}
}

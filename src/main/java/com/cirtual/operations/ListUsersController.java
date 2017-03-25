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
/**
 * @author Pratish
 * @version : 1.0.0
 * 
 *          Lists all users to authenticated users with valid tokenId
 */
public class ListUsersController {
	@Autowired
	private UserDao userDAO;

	@Autowired
	private TokenService tokenService;

	// method will all messages from all users.

	/**
	 * End point : /list-all-users
	 * <p>
	 * Returns a list of users as a List of String. It only requires a valid
	 * authenticated tokenID
	 * <p>
	 * It will return a null list if there are no users.
	 * 
	 * @param tokenId
	 *            A unique identifier for a particular registered user.
	 * @return List of users
	 *         {@link com.cirtual.operations.TokenController#generateToken(String, String)}
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/list-all-users")
	public List<String> listAllUsers(@RequestParam(value = "tokenId") String tokenId) {
		int userId = -1;
		userId = tokenService.validateTokenId(tokenId);
		if (userId < 0) {
			return null;
		}
		return userDAO.findAllUsers();
	}
}

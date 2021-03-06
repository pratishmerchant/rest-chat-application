package com.cirtual.operations;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cirtual.data.ProfileDao;
import com.cirtual.data.UserDao;
import com.cirtual.data.UsernameProjection;
import com.cirtual.data.Users;
import com.cirtual.operations.service.TokenService;


/**
 * End point to search for Users based on username and if profile is setup with First and Last Name.
 * 
 * @author Pratish
 * @version : 1.0.0
 */


@RestController
public class SearchUsersController {
	@Autowired
	private UserDao userDAO;
	
	@Autowired
	private ProfileDao profileDAO;

	@Autowired
	private TokenService tokenService;

	// method to search all users.
	
	/**End-point : /search-users
	 * <p>
	 * Service to search for other users using partial match on username. If profile exists then on first and lastname as well.  
	 * <p>
	 * 
	 * @param tokenId A unique identifier for a particular registered user.
	 * @param pattern Partial string that will be used to query
	 * @return List of usernames.
	 * 
	 */
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/search-users")
	public List<String> searchUsers(@RequestParam(value = "tokenId") String tokenId,
			@RequestParam(value = "pattern") String pattern) {

		int userId = -1;
		List<String> searchResult = null;
		
		userId = tokenService.validateTokenId(tokenId);
		if (userId < 0) {
			return null;
		}

		try {
			searchResult = userDAO.findUsersLike(pattern);
		}catch(Exception e){
			System.out.println("exp "+e);
			return null;
		}

		// Check from profile for first name and last name as well.
		try {
			searchResult.addAll( profileDAO.findFirstnameLike(pattern));
		}catch(Exception e){
			System.out.println("exception" + e);
			return null;
		}
		
		try {
			searchResult.addAll( profileDAO.findLastnameLike(pattern));
		}catch(Exception e){
			System.out.println("exception" + e);
			return null;
		}

		return (List<String>)searchResult;
	}
}

package com.cirtual.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cirtual.data.UserDao;
import com.cirtual.data.Users;

/**
 * End point for new users to register. Allows new users to register by providing unique username and password.
 * @author Pratish
 * @version : 1.0.0
 */


@RestController
public class RegisterController {
	@Autowired
	private UserDao userDAO;

	// Register a user
	
	/**End-point : /register-user
	 * <p>
	 * Provides new users to register for services.  
	 * <p>
	 * 
	 * @param username Username should be unique and not more than 45 characters.
	 * @param password Password should be greater than 6 characters.Stores securely as a Hash.(No other checks)
	 * @return Returns a unique id for new user if successful 
	 * 
	 */
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/register-user")
	public String register(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		String id = "";
		Users checkOldUser = userDAO.findByUsername(username.toLowerCase());
		if (checkOldUser != null)
			return "User alreay exsists. Please use a different username";
		if(password.length() < 6 )
			return "Password should not be less than 6 characters";

		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			Users newUser = new Users(username.toLowerCase(), hashedPassword);
			userDAO.save(newUser);
			id = String.valueOf(newUser.getId());
		} catch (Exception ex) {
			return "Error in creating user : " + username;
		}

		return "User successfully created with ID : " + id;
	}
}

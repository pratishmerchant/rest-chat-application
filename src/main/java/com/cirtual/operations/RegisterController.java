package com.cirtual.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cirtual.data.UserDao;
import com.cirtual.data.Users;

@RestController
public class RegisterController {
	@Autowired
	private UserDao userDAO;

	// Register a user
	@RequestMapping(method = RequestMethod.POST, value = "/register-user")
	public String register(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		String id = "";
		Users checkOldUser = userDAO.findByUsername(username);
		if (checkOldUser != null)
			return "User alreay exsists. Please use a different username";

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

package com.cirtual.operations;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cirtual.data.Token;
import com.cirtual.data.TokenDao;
import com.cirtual.data.UserDao;
import com.cirtual.data.Users;

@RestController
public class TokenController {

	@Autowired
	TokenDao tokenDAO;

	@Autowired
	UserDao userDAO;

	// Generate token

	@RequestMapping(method = RequestMethod.POST, value = "/generate-token")
	public String register(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		UUID token = null;
		Users checkUser = userDAO.findByUsername(username);
		if (checkUser == null)
			return "User doesnt not exsist. Please register the user first";

		BCryptPasswordEncoder digestor = new BCryptPasswordEncoder();
		if (!digestor.matches(password, checkUser.getPassword()))
			return "The password is incorrect";

		try {

			Token checkToken = tokenDAO.findByUserId(checkUser.getId());
			token = UUID.randomUUID();
			Timestamp generatedAt = new Timestamp(System.currentTimeMillis());
			if (checkToken != null) {
				System.out.println("TOKEN  : " +checkToken);
				checkToken.setTokenNo(token.toString());
				checkToken.setGenerated(generatedAt);
				tokenDAO.save(checkToken);
			} else {
				Token newToken = new Token(checkUser.getId(),token.toString(),generatedAt);
				System.out.println("else TOKEN  : "+newToken);
				tokenDAO.save(newToken);
			}

		} catch (Exception ex) {
			return "Error in generating Token ";
		}

		return token.toString();
	}
}

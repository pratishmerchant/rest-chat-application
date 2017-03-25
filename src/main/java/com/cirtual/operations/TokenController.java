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


/**
 * End point to generate a unique token which is valid for 10 mins and used for further services.
 * 
 * @author Pratish
 * @version : 1.0.0
 */


@RestController
public class TokenController {

	@Autowired
	TokenDao tokenDAO;

	@Autowired
	UserDao userDAO;

	// Generate token

	/**End-point : /generate-token
	 * <p>
	 * Endpoint service for generating tokenId based which is valid 10 mins. 
	 * <p>
	 * The user need to be registered in order to generate token.
	 * @param username Username should be unique and not more than 45 characters.
	 * @param password Password should be greater than 6 characters.Stores securely as a Hash.(No other checks)
	 * @return Returns a unique tokenId 
	 * 
	 */
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/generate-token")
	public String generateToken(@RequestParam(value = "username") String username,
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

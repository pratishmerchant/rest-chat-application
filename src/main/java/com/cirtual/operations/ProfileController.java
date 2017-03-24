package com.cirtual.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cirtual.data.MessageDao;
import com.cirtual.data.Profile;
import com.cirtual.data.ProfileDao;
import com.cirtual.data.UserDao;
import com.cirtual.data.Users;
import com.cirtual.operations.service.TokenService;

@RestController
public class ProfileController {


	@Autowired
	private ProfileDao profileDAO;

	@Autowired
	private TokenService tokenService;

	// Method to create Profile
	@RequestMapping(method = RequestMethod.POST, value = "/create-profile")
	public String createProfile(@RequestParam(value = "tokenId") String tokenId,
			@RequestParam(value = "firstname") String firstname, @RequestParam(value = "lastname") String lastname,
			@RequestParam(value = "phone") String phone) {

		String profileID = "";
		int userId = -1;

		userId = tokenService.validateTokenId(tokenId);
		if (userId < 0) {
			return null;
		}
		// Assumption Country US and no extension
		Object o = phone;
		if ((phone != null || !phone.equals("")) && phone.length() != 10 && (o instanceof Integer))
			return "phone number must be 10 digits";
		try {
			Profile checkProfile = profileDAO.findByUserid(userId);

			if (checkProfile == null) {
				Profile newProfile = new Profile(userId, firstname, lastname, Double.parseDouble(phone));
				System.out.println(newProfile.toString());
				profileDAO.save(newProfile);
				profileID =  String.valueOf(newProfile.getIdprofile());
				return "Profile created successfully with ID : " + profileID ;
			}
				checkProfile.setFirstname(firstname);
				checkProfile.setLastname(lastname);
				checkProfile.setPhone(Double.parseDouble(phone));
				
				profileDAO.save(checkProfile);
			

			profileID = String.valueOf(checkProfile.getIdprofile());
		} catch (Exception e) {
			System.out.println("exp" + e);
			return "Error in creating profile";
		}
		return "Profile updated successfully for " + profileID;

	}

}

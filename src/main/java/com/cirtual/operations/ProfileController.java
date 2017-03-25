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
import com.cirtual.data.ProfileProjection;
import com.cirtual.data.UserDao;
import com.cirtual.data.Users;
import com.cirtual.operations.service.TokenService;

/**
 * End point related to profile creation. This is implemented optionally for a
 * registered user.
 * 
 * 
 * @author Pratish
 * @version : 1.0.0
 */


@RestController
public class ProfileController {

	@Autowired
	private ProfileDao profileDAO;
	
	@Autowired
	private UserDao userDAO;

	@Autowired
	private TokenService tokenService;

	// Method to create Profile
	/**
	 * End-point : /create-profile.
	 * Optionally create a user profile for a registered user. Currently its limited to just firstname , lastname and
	 * phone number. If user already exists , then updates profile information
	 * with provided values. Anyone can view Profile information. The fields can
	 * be left null if user doesnt want to share.
	 * 
	 * @param tokenId
	 *            A unique identifier for a particular registered user.
	 * @param firstname
	 *            First name of the user
	 * @param lastname
	 *            Last name of the user
	 * @param phone
	 *            10 digit phone number of the user(assumes that country is US
	 *            for now.)
	 * @return returns successful message with profileID.
	 */

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
		if (!phone.equals("") && phone.length() != 10 && (o instanceof Integer))
		
		try {
			Profile checkProfile = profileDAO.findByUserid(userId);

			if (checkProfile == null) {
				Profile newProfile = new Profile(userId, firstname, lastname, phone);
				System.out.println(newProfile.toString());
				profileDAO.save(newProfile);
				profileID = String.valueOf(newProfile.getIdprofile());
				return "Profile created successfully with ID : " + profileID;
			}
			checkProfile.setFirstname(firstname);
			checkProfile.setLastname(lastname);
			checkProfile.setPhone(phone);

			profileDAO.save(checkProfile);

			profileID = String.valueOf(checkProfile.getIdprofile());
		} catch (Exception e) {
			System.out.println("exp" + e);
			return "Error in creating profile";
		}
		return "Profile updated successfully for " + profileID;

	}
	
	/**
	 * End-point : /view-profile.
	 * It is used to view a profile provided we know the username of that user and the user profile is setup.
	 * 
	 * 
	 * @param tokenId A unique identifier for a particular registered user.
	 * @param user2 username of the other user
	 * @return Returns the firstname , lastname and phone number
	 */
	
	@RequestMapping("/view-profile")
	public ProfileProjection viewProfile(@RequestParam(value = "username") String username) {
		ProfileProjection user2Profile  = null;
		
		if (username == null)
			return null;

		Users otherUser = userDAO.findByUsername(username);
		if (otherUser == null)
			return null;

		try {
			Profile checkProfile = profileDAO.findByUserid(otherUser.getId());

			if (checkProfile == null) {
				return null;
			}
			
			user2Profile = profileDAO.findByIdprofile(checkProfile.getIdprofile());
			System.out.println(user2Profile.getFirstname() +" " +user2Profile.getLastname()+" "+user2Profile.getPhone());
		} catch (Exception e) {
			return null;
		}
		return user2Profile;

	}

}

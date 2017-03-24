package com.cirtual.operations;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cirtual.data.MessageDao;
import com.cirtual.data.Messages;
import com.cirtual.data.Profile;
import com.cirtual.data.ProfileDao;
import com.cirtual.data.UserDao;
import com.cirtual.data.UsernameProjection;
import com.cirtual.data.Users;

@RestController
public class OperationController {

	@Autowired
	private UserDao userDAO;

	@Autowired
	private MessageDao messageDAO;

	@Autowired
	private ProfileDao profileDAO;

	// Register a user
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public String register(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		String id = "";
		Users checkOldUser = userDAO.findByUsername(username);
		if (checkOldUser != null)
			return "User alreay exsists. Please use a different username";

		try {
			Users newUser = new Users(username.toLowerCase(), password);
			userDAO.save(newUser);
			id = String.valueOf(newUser.getId());
		} catch (Exception ex) {
			return "Error in creating user : " + username;
		}

		return "User successfully created with ID : " + id;
	}

	// Send Message
	@RequestMapping(method = RequestMethod.POST, value = "/sendmessage")
	public String sendMessage(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, @RequestParam(value = "toUser") String toUser,
			@RequestParam(value = "message") String message) {

		String chatID = "";

		if (username == null)
			return "User Name cannot be  null";
		if (toUser == null)
			return "Sender Name cannot be  null";

		Users checkUser = userDAO.findByUsername(username);
		Users checkToUser = userDAO.findByUsername(toUser);

		if (checkUser == null)
			return "User does not exsist in Database";
		if (checkToUser == null)
			return "Sender does not exsist in Database";
		if (!checkUser.getPassword().equals(password))
			return "Password does not match the username";

		Messages newMessage = new Messages(checkToUser.getId(), checkUser.getId(), message);
		System.out.println(newMessage.toString());

		messageDAO.save(newMessage);
		chatID = String.valueOf(newMessage.getId());

		return "Message sent successfully with ID : " + chatID + " at : " + newMessage.getSent_at();

	}

	// Get Messages . Here we will overload method with different parameters

	// Method will get unread messages from all users.
	@RequestMapping(method = RequestMethod.POST, value = "/getunreadmessages")
	public List<Messages> getMessages(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		if (username == null)
			return null;

		Users checkUser = userDAO.findByUsername(username);
		if (checkUser == null)
			return null;

		List<Messages> unReadMessage = messageDAO.findByReadValAndUser1Id(0, checkUser.getId());
		messageDAO.updateReadVal(checkUser.getId(), 1);

		return unReadMessage;
	}

	// method will get unread messages from between timestamp provided.
	@RequestMapping(method = RequestMethod.POST, value = "/getmessagesbytime")
	public List<Messages> getMessages(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "startTime") String startTimeString,
			@RequestParam(value = "endTime") String endTimeString) {

		List<Messages> unReadMessage = null;
		if (username == null)
			return null;

		Users checkUser = userDAO.findByUsername(username);
		if (checkUser == null)
			return null;

		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date startParsed = (Date) formatter.parse(startTimeString);
			Date endParsed = (Date) formatter.parse(endTimeString);
			Timestamp startTime = new java.sql.Timestamp(startParsed.getTime());
			Timestamp endTime = new java.sql.Timestamp(endParsed.getTime());
			unReadMessage = messageDAO.findByUser1IdAndSentAtBetween(checkUser.getId(), startTime, endTime);
		} catch (Exception e) {// this generic but you can control another types
								// of exception
			System.out.println("Timestamp should be in format yyyy-MM-dd hh:mm:ss ");
		}

		return unReadMessage;
	}

	// method will get messages from particular users.
	@RequestMapping(method = RequestMethod.POST, value = "/getmessagesbyuser")
	public List<Messages> getMessages(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, @RequestParam(value = "user2") String user2) {

		List<Messages> unReadUserMessage = null;
		if (username == null || user2 == null)
			return null;

		Users checkUser = userDAO.findByUsername(username);
		Users otherUser = userDAO.findByUsername(user2);
		if (checkUser == null || otherUser == null)
			return null;

		unReadUserMessage = messageDAO.findByUser1IdAndUser2Id(checkUser.getId(), otherUser.getId());

		return unReadUserMessage;
	}

	// method will all messages from all users.
	@RequestMapping(method = RequestMethod.POST, value = "/getallmessages")
	public List<Messages> getAllMessages(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		List<Messages> allMessages = null;
		if (username == null)
			return null;

		Users checkUser = userDAO.findByUsername(username);

		if (checkUser == null)
			return null;

		allMessages = messageDAO.findByUser1Id(checkUser.getId());

		return allMessages;
	}

	// method will all messages from all users.
	@RequestMapping(method = RequestMethod.POST, value = "/listAllUsers")
	public List<String> listAllUsers(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		if (username == null)
			return null;

		Users checkUser = userDAO.findByUsername(username);

		if (checkUser == null)
			return null;

		if (!checkUser.getPassword().equals(password))
			return null;

		return userDAO.findAllUsers();
	}
	
	// method will all messages from all users.
	@RequestMapping(method = RequestMethod.POST, value = "/searchusers")
	public List<UsernameProjection> searchUsers(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,@RequestParam(value = "pattern")String pattern) {
		System.out.println("Came here");
		List<UsernameProjection> searchResult = null;
		if (username == null)
			return null;

		Users checkUser = userDAO.findByUsername(username);

		if (checkUser == null)
			return null;

		if (!checkUser.getPassword().equals(password))
			return null;
		
//		System.out.println("Data "+userDAO.findAllUsers(pattern).toString());
		searchResult = userDAO.findByUsernameLike(username);
		
		//Check for first name and last name as well.
		
		


		return searchResult;
	}

	// Method to create Profile
	@RequestMapping(method = RequestMethod.POST, value = "/createprofile")
	public String createProfile(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, @RequestParam(value = "firstname") String firstname,
			@RequestParam(value = "lastname") String lastname, @RequestParam(value = "phone") String phone) {

		String profileID = "";

		if (username == null)
			return "User Name cannot be  null";

		Users checkUser = userDAO.findByUsername(username);

		if (checkUser == null)
			return "User does not exsist in Database";
		if (!checkUser.getPassword().equals(password))
			return "Password does not match the username";

		if ((phone != null || !phone.equals("")) && phone.length() != 10)
			return "phone number must be 10 digits";

		Profile newProfile = new Profile(checkUser.getId(), firstname, lastname, Double.parseDouble(phone));
		System.out.println(newProfile.toString());

		profileDAO.save(newProfile);
		profileID = String.valueOf(newProfile.getIdprofile());

		return "Message sent successfully with ID : " + profileID;

	}

	// Method to create Profile
	@RequestMapping(method = RequestMethod.POST, value = "/updateprofile")
	public String updateProfile(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, @RequestParam(value = "firstname") String firstname,
			@RequestParam(value = "lastname") String lastname, @RequestParam(value = "phone") String phone) {

		String profileID = "";

		if (username == null)
			return "User Name cannot be  null";

		Users checkUser = userDAO.findByUsername(username);

		if (checkUser == null)
			return "User does not exsist in Database";
		if (!checkUser.getPassword().equals(password))
			return "Password does not match the username";

		if (phone.length() != 10)
			return "phone number must be 10 digits";
		try {

			Profile userProfile = profileDAO.findByUserid(checkUser.getId());
			userProfile.setFirstname(firstname);
			userProfile.setLastname(lastname);
			userProfile.setPhone(Double.parseDouble(phone));
			profileDAO.save(userProfile);
			profileID = String.valueOf(userProfile	.getIdprofile());
		} catch (Exception ex) {
			return "Error updating the user: " + ex.toString();
		}
		return "Message sent successfully with ID : " + profileID;

	}

}

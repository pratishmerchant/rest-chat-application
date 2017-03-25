package com.cirtual.operations;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cirtual.data.MessageDao;
import com.cirtual.data.MessageProjection;
import com.cirtual.data.Messages;
import com.cirtual.data.UserDao;
import com.cirtual.data.Users;
import com.cirtual.operations.service.TokenService;


/**
 * End point for viewing received messages . Currently 4 different options to view messages : unread , between timestamp, by user and all messages.
 *  
 * @author Pratish
 * @version : 1.0.0
 */


@RestController
public class RecieveMessageController {
	@Autowired
	private UserDao userDAO;

	@Autowired
	private MessageDao messageDAO;

	@Autowired
	private TokenService tokenService;

	// Method will get unread messages from all users.
	
	/**End-point : /get-unread-messages
	 * <p>
	 * This method provides user with all unread messages. 
	 * <p>
	 * @param tokenId  A unique identifier for a particular registered user.
	 * @return List of messages as json with timestamp
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/get-unread-messages")
	public List<MessageProjection> getMessages(@RequestParam(value = "tokenId") String tokenId){

		List<MessageProjection> unReadMessage = null;
		int userId = -1;
		userId = tokenService.validateTokenId(tokenId);
		if (userId < 0)
			return unReadMessage;
		try {
			unReadMessage = messageDAO.findByReadValAndUser1Id(0, userId);
			messageDAO.updateReadVal(userId, 1);
		} catch (Exception e) {
			return unReadMessage;
		}

		return unReadMessage;
	}

	// method will get messages between timestamp provided.

	/**End-point : /get-messages-by-time
	 * <p>
	 * This method provides user with all  messages between timestamp interval values. 
	 * <p>
	 * @param tokenId  A unique identifier for a particular registered user.
	 * @param startTimeString Timestamp for start of the interval
	 * @param endTimeString	Timestamp for end of interval
	 * @return List of message as json with timestamp
	 *
	 */
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/get-messages-by-time")
	public List<MessageProjection> getMessages(@RequestParam(value = "tokenId") String tokenId,
			@RequestParam(value = "startTime") String startTimeString,
			@RequestParam(value = "endTime") String endTimeString) {

		List<MessageProjection> timeMessages = null;
		int userId = -1;
		userId = tokenService.validateTokenId(tokenId);
		if (userId < 0)
			return timeMessages;

		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date startParsed = (Date) formatter.parse(startTimeString);
			Date endParsed = (Date) formatter.parse(endTimeString);
			Timestamp startTime = new java.sql.Timestamp(startParsed.getTime());
			Timestamp endTime = new java.sql.Timestamp(endParsed.getTime());

			timeMessages = messageDAO.findByUser1IdAndSentAtBetween(userId, startTime, endTime);

		} catch (Exception e) {// this generic but you can control another types
								// of exception
			System.out.println("Timestamp should be in format yyyy-MM-dd hh:mm:ss ");
		}

		return timeMessages;
	}

	// method will get messages from particular users.
	
	/**End-point : /get-messages-by-user
	 * <p>
	 * This method provides user with all messages from another user. 
	 * <p>
	 * @param tokenId A unique identifier for a particular registered user.
	 * @param user2  Username of another user , which can be found using list or search user
	 * @return List of message as json with timestamp
	 * 
	 */
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/get-messages-by-user")
	public List<MessageProjection> getMessages(@RequestParam(value = "tokenId") String tokenId,
			@RequestParam(value = "user2") String user2) {

		List<MessageProjection> userMessage = null;
		int userId = -1;
		userId = tokenService.validateTokenId(tokenId);
		if (userId < 0)
			return userMessage;
		if (user2 == null)
			return null;

		Users otherUser = userDAO.findByUsername(user2);
		if (otherUser == null)
			return null;

		try {
			userMessage = messageDAO.findByUser1IdAndUser2Id(userId, otherUser.getId());
		} catch (Exception e) {
			return userMessage;
		}

		return userMessage;
	}

	// method will all messages from all users.
	/**End-point : /get-all-messages
	 * <p> 
	 * This method provides user with all messages. 
	 * <p>
	 * @param tokenId List of message as json with timestamp
	 * @return List of message as json with timestamp
	 * 
	 */
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/get-all-messages")
	public List<MessageProjection> getAllMessages(@RequestParam(value = "tokenId") String tokenId) {

		List<MessageProjection> allMessages = null;
		int userId = -1;
		userId = tokenService.validateTokenId(tokenId);
		if (userId < 0)
			return allMessages;
		try {
			allMessages = messageDAO.findByUser1Id(userId);
		} catch (Exception e) {
			return allMessages;
		}
		return allMessages;
	}

}

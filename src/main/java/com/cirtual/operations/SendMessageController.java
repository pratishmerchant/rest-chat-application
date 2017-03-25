package com.cirtual.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cirtual.data.MessageDao;
import com.cirtual.data.Messages;
import com.cirtual.data.UserDao;
import com.cirtual.data.Users;
import com.cirtual.operations.service.TokenService;



/**
 * 
 * End point to send message to a particular user using username.
 * 
 * @author Pratish
 * @version : 1.0.0
 */


@RestController
public class SendMessageController {
	@Autowired
	private UserDao userDAO;

	@Autowired
	private MessageDao messageDAO;
	
	@Autowired
	private TokenService tokenService;

	// Send Message
	/**End-point : /send-message
	 * <p>
	 * Endpoint service for sending messages to a particular user using their username. 
	 * <p>
	 * Can use listAllUsers or searchUsers to find username. 
	 * @param tokenId A unique identifier for a particular registered user.
	 * @param toUser Username of the person whom the message will be directed to 
	 * @param message Text that will contain message text
	 * @return mesageid would be reported for succesfully transmission.
	 * 
	 */
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/send-message")
	public String sendMessage(@RequestParam(value = "tokenId") String tokenId,
			@RequestParam(value = "toUser") String toUser, @RequestParam(value = "message") String message) {

		String chatID= "";
		int userId = -1;
		userId = tokenService.validateTokenId(tokenId);
		if( userId < 0){
			return "The token has is wrong or expired . Please generate a new one to continue";
		}
		
		if (toUser == null)
			return "Sender Name cannot be  null";
		Users checkToUser = userDAO.findByUsername(toUser);
		if (checkToUser == null)
			return "Sender does not exsist in Database";
		

		Messages newMessage = new Messages(checkToUser.getId(), userId, message);
		System.out.println(newMessage.toString());

		messageDAO.save(newMessage);
		chatID = String.valueOf(newMessage.getIdmessages());

		return "Message sent successfully with ID : " + chatID + " at : " + newMessage.getSentAt();

	}
}

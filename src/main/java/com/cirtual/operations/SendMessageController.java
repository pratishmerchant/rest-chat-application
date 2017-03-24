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


@RestController
public class SendMessageController {
	@Autowired
	private UserDao userDAO;

	@Autowired
	private MessageDao messageDAO;
	
	@Autowired
	private TokenService tokenService;

	// Send Message
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

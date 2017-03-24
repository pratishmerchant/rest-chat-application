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

@RestController
public class RecieveMessageController {
	@Autowired
	private UserDao userDAO;

	@Autowired
	private MessageDao messageDAO;

	@Autowired
	private TokenService tokenService;

	// Method will get unread messages from all users.
	@RequestMapping(method = RequestMethod.POST, value = "/get-unread-messages")
	public List<MessageProjection> getMessages(@RequestParam(value = "tokenId") String tokenId) {

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

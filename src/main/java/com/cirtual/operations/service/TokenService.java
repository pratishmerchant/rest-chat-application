package com.cirtual.operations.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cirtual.data.Token;
import com.cirtual.data.TokenDao;

@Service
public class TokenService {
	
	@Autowired
	TokenDao tokenDAO;
	
	public int validateTokenId(String tokenId){
		System.out.println("Coming here");
		if (tokenId == null) return -1;
		
		Token checkToken = tokenDAO.findByTokenNo(tokenId);
		
		if (checkToken == null) return -1;

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		
		//covert to minuutes
		double timeDiff = (currentTime.getTime() - checkToken.getGenerated().getTime())/60000.0;
		System.out.println(timeDiff);
		
		// 10 min time out
		if (timeDiff > 10){
			checkToken.setGenerated(null);
			checkToken.setTokenNo(null);
			tokenDAO.save(checkToken);
			return -1;
		}else {
			//Update token time 
			checkToken.setGenerated(currentTime);
			tokenDAO.save(checkToken);
		}
		
		return checkToken.getUserId();
	}

}

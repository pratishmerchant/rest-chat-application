package com.cirtual.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenDao  extends JpaRepository<Token, Long>{
	Token findByUserId(int userId);
	
	Token findByTokenNo(String tokenno);
}

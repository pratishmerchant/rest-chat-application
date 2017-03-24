package com.cirtual.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Transactional
public interface UserDao extends JpaRepository<Users, Long>{
	public Users findByUsername(String username);
	
	
//	@Query(value = "SELECT u.username FROM Users u WHERE u.username LIKE %:pattern%")
//	public List<String> findAllUsers(@Param("pattern") String pattern);
	
	public List<UsernameProjection> findByUsernameLike(String username);
	
	
	@Query(value = "SELECT u.username FROM Users u ")
	public List<String> findAllUsers();
}

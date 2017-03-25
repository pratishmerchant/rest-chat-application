package com.cirtual.data;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface ProfileDao extends JpaRepository<Profile, Long>{
	Profile findByUserid(int userid);
	
	@Query(value = "SELECT p.firstname FROM Profile p WHERE p.firstname LIKE %?1% ")
	public List<String> findFirstnameLike(@Param("pattern") String pattern);
	
	@Query(value = "SELECT p.lastname FROM Profile p WHERE p.lastname LIKE %?1% ")
	public List<String> findLastnameLike(@Param("pattern") String pattern);
}

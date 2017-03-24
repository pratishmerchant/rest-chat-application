package com.cirtual.data;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ProfileDao extends JpaRepository<Profile, Long>{
	Profile findByUserid(int userid);
}

package com.cirtual.data;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



@Transactional
public interface MessageDao extends CrudRepository<Messages, Long> {
	public List<MessageProjection> findByReadValAndUser1Id(int readVal,int user1Id);
	public List<MessageProjection> findTop10ByReadValAndUser1Id(int readVal,int user1Id);
	public List<MessageProjection> findByUser1IdAndSentAtBetween(int user1Id,Timestamp start,Timestamp end);
	public List<MessageProjection> findByUser1IdAndUser2Id(int user1Id,int user2Id);
	public List<MessageProjection> findByUser1Id(int user1Id);

	@Modifying
	@Query("UPDATE Messages m SET m.readVal = ?2 WHERE m.user1Id = ?1")
	int updateReadVal(int user1_id, int read_val);
	

}

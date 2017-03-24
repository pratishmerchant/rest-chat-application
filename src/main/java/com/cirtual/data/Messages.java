package com.cirtual.data;



import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="messages")
public class Messages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idmessages;
	
	@NotNull
	@Column(name = 	"user1_id")
	private int user1Id;
	
	@NotNull
	@Column(name = "user2_id")
	private int user2Id;
	
	@NotNull
	@Column(name = 	"message_text")
	private String messageText;
	
	@NotNull
	@Column(name = 	"sent_at")
	private Timestamp sentAt;
	
	@NotNull
	@Column(name = 	"read_val")
	private int readVal;
	

	public Messages() {}
	
	public int getIdmessages() {
		return idmessages;
	}

	public void setId(int id) {
		this.idmessages = id;
	}

	

	public String getMessageText() {
		return messageText;
	}

	public void setMessage_text(String message) {
		this.messageText = message;
	}

	public Timestamp getSentAt() {
		return sentAt;
	}



	public Messages(int user1_id, int user2_id, String message_text) {
		super();
		this.user1Id = user1_id;
		this.user2Id = user2_id;
		this.messageText = message_text;
		this.sentAt = new Timestamp(System.currentTimeMillis());
		this.readVal = 0 ;
	}

	public Messages(int user1_id, int user2_id, String message_text, Timestamp sent_at, int read) {
		super();
		this.user1Id = user1_id;
		this.user2Id = user2_id;
		this.messageText = message_text;
		this.sentAt = sent_at;
		this.readVal = read;
	}
	
	
	public int getUser1_id() {
		return user1Id;
	}

	public void setUser1_id(int user1_id) {
		this.user1Id = user1_id;
	}

	public int getUser2_id() {
		return user2Id;
	}

	public void setUser2_id(int user2_id) {
		this.user2Id = user2_id;
	}

	public int getRead() {
		return readVal;
	}

	public void setRead(int read) {
		this.readVal = read;
	}

	@Override
	public String toString() {
		return "Messages [idmessages=" + idmessages + ", user1_id=" + user1Id + ", user2_id=" + user2Id
				+ ", message_text=" + messageText + ", sent_at=" + sentAt + ", read=" + readVal + "]";
	}

	
	
	
	
}

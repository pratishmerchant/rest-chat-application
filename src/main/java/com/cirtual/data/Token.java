package com.cirtual.data;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



/**
 * Maps to token table in db. Stores generated token with its timestamp
 * 
 * @author Pratish
 * @version 1.0.0
 */
@Entity
@Table(name = "token")
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idtoken;

	@NotNull
	@Column(name = "userid")
	private int userId;

	@Column(name = "tokenno")
	private String tokenNo;

	@Column(name = "generatedat")
	private Timestamp generatedAt;
	
	public Token() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Token(int userId, String tokenNo, Timestamp generated) {
		super();
		this.userId = userId;
		this.tokenNo = tokenNo;
		this.generatedAt = generated;
	}
	public Token(int userId) {
		super();
		this.userId = userId;

	}

	public int getIdtoken() {
		return idtoken;
	}

	public void setIdtoken(int idtoken) {
		this.idtoken = idtoken;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTokenNo() {
		return tokenNo;
	}

	public void setTokenNo(String tokenNo) {
		this.tokenNo = tokenNo;
	}

	public Timestamp getGenerated() {
		return generatedAt;
	}

	public void setGenerated(Timestamp generated) {
		this.generatedAt = generated;
	}

	@Override
	public String toString() {
		return "Token [idtoken=" + idtoken + ", userId=" + userId + ", tokenNo=" + tokenNo + ", generated=" + generatedAt
				+ "]";
	}
	

}

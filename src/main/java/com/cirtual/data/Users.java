package com.cirtual.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/** 
 * This class maps to Users table in Database. It has a stores the username and password of the user. 
 * 
 * @author Pratish
 * @version : 1.0.0
 *
 *
 */



@Entity
@Table(name="users")
public class Users {

	/**
	 * Unique Identifier for the table
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/**
	 * Stores username
	 */
	
	@NotNull
	@Column(name = "username")
	private String username;
	
	/**
	 * Password stored as hash using BCrypt
	 */
	
	@NotNull
	@Column(name = "password")
	private String password;
	
	
	public Users() {
		super();
	}


	public Users(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

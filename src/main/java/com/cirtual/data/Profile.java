package com.cirtual.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "profile")
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idprofile;
	
	@NotNull
	@Column(name = "userid")
	private int userid;
	
	@NotNull
	@Column(name = "firstname")
	private String firstname;
	
	@NotNull
	@Column(name = "lastname")
	private String lastname;
	
	@NotNull
	@Column(name = "phone")
	private double phone;
	
	

	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Profile(int userid, String firstname, String lastname, double phone) {
		super();
		this.userid = userid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
	}

	public int getIdprofile() {
		return idprofile;
	}



	public void setIdprofile(int idprofile) {
		this.idprofile = idprofile;
	}



	public int getUserid() {
		return userid;
	}



	public void setUserid(int userid) {
		this.userid = userid;
	}



	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public double getPhone() {
		return phone;
	}



	public void setPhone(double phone) {
		this.phone = phone;
	}
	
	@Override
	public String toString() {
		return "Profile [idprofile=" + idprofile + ", userid=" + userid + ", firstname=" + firstname + ", lastname="
				+ lastname + ", phone=" + phone + "]";
	}
	
}

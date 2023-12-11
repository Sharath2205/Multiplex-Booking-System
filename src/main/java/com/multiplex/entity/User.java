package com.multiplex.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", updatable = false)
	private int userId;

	@Column(name = "user_name", length = 45, nullable = false)
	private String userName;
	
	@Column(name = "user_password", nullable = false)
	private String password;
	
	@Column(name = "user_type", nullable = false)
	private char userType;
	
	@Column(name = "mobile_no", length = 10, nullable = false)
	private long mobileNumber;
	
	@Column(name = "email_id", length = 225, unique = true)
	private String emailId;
	
	@OneToMany(mappedBy = "user")
	private List<Booking> booking;
	
	public User() {
		
	}

	public User(int userId, String userName, char userType, String userPassword, long mobileNumber, String emailId,
			List<Booking> booking) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userType = userType;
		this.password = userPassword;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.booking = booking;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getUserType() {
		return userType;
	}

	public void setUserType(char userType) {
		this.userType = userType;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<Booking> getBooking() {
		return booking;
	}

	public void setBooking(List<Booking> booking) {
		this.booking = booking;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + password + ", userType="
				+ userType + ", mobileNumber=" + mobileNumber + ", emailId=" + emailId + ", booking=" + booking + "]";
	}
}

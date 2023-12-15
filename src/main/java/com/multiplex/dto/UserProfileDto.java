package com.multiplex.dto;

import java.time.LocalDate;

public class UserProfileDto {
	private int userId;
	private String userName;
	private String emailId;
	private String mobileNumber;
	private LocalDate dateOfBirth;

	public UserProfileDto(int userId, String userName, String emailId, String mobileNumber, LocalDate dateOfBirth) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.dateOfBirth = dateOfBirth;
	}

	public UserProfileDto() {
		super();
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}

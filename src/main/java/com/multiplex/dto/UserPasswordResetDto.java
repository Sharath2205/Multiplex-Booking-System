package com.multiplex.dto;

public class UserPasswordResetDto {
	private String emailId;
	private String password;
	private String confirmPassword;

	public UserPasswordResetDto() {
		super();
	}

	public UserPasswordResetDto(String emailId, String password, String confirmPassword) {
		super();
		this.emailId = emailId;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}

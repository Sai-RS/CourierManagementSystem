package com.hexaware.entity;

public class User {

	private long userId;
	private String userName;
	private String email;
	private String password;
	private String contactNumber;
	private String address;
	
	
	public User(long userId, String userName, String email, String password, String contactNumber, String address) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.contactNumber = contactNumber;
		this.address = address;
	}
	
	public User() {
		
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
	@Override
	public String toString() {
		
		
		return "userId: " + userId + "\nuserName: " + userName + "\nemail: " + email + "\npassword: " + password
				+ "\ncontactNumber: " + contactNumber + "\naddress: " + address + "\n\n";
				
	}
	
}

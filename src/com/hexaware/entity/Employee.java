package com.hexaware.entity;

public class Employee {

	// employeeID , employeeName , email , contactNumber , role String, salary
	
	private long employeeId;
	private String employeeName;
	private String email;
	private String contactNumber;
	private String role;
	private double salary;
	
	public Employee(long employeeId,String name,String email,String contact,String role,double salary) {
		this.employeeId = employeeId;
		this.employeeName = name;
		this.email = email;
		this.contactNumber = contact;
		this.role = role;
		this.salary = salary;
	}
	
	public Employee() {
		
	}
	
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", email=" + email
				+ ", contactNumber=" + contactNumber + ", role=" + role + ", salary=" + salary + ", toString()="
				+ super.toString() + "]";
	}
	
}

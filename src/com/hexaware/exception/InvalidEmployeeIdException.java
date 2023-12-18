package com.hexaware.exception;

public class InvalidEmployeeIdException extends Exception{
	int empId;
	
	public InvalidEmployeeIdException(int empId) {
		this.empId = empId;
	}
	
	public String toString() {
		return String.format("There is no Employee with the Employee Id %s",empId);
	}

}

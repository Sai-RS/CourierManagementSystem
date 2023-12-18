package com.hexaware.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.hexaware.controller.CustomerDataValidation;
import com.hexaware.entity.Employee;

public class CourierAdminServiceImpl extends CourierUserServiceImpl implements ICourierAdminService{

	@Override
	public boolean addCourierStaff() throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		CourierServiceDb csd = new CourierServiceDb();

		Employee employee = new Employee();
		
		
		String name,contactNumber,email;
		
		// Name
		
		System.out.println("\nEnter your Name: ");
		name = bf.readLine();
		
		while(!CustomerDataValidation.verifyName(name)) {
			System.out.println("The Name should contain only alphabetical letters.\n");
			System.out.println("Re-Enter your name: ");
			name = bf.readLine();
		}	
		employee.setEmployeeName(name);
		
		// Email
		
		System.out.println("\nEnter your Email: ");
		email = bf.readLine();
		
		while(!CustomerDataValidation.verifyEmail(email)) {
			System.out.println("The given email is invalid.\n");
			System.out.println("Re-Enter your email: ");
			email = bf.readLine();
		}	
		employee.setEmail(CustomerDataValidation.unCapitalize(email));
		
		
		// Contact Number
		
		System.out.println("\nEnter your Contact Number: ");
		contactNumber = bf.readLine();
		
		while(!CustomerDataValidation.verifyContactNumber(contactNumber)) {
			System.out.println("Please enter your contact number in ###-###-#### format.\n");
			System.out.println("Re-Enter your Contact Number: ");
			contactNumber = bf.readLine();
		}	
		employee.setContactNumber(contactNumber);
		
		employee.setRole("Courier");
		employee.setSalary(25000);
		
		return csd.insertEmployeeDetails(employee);
		
	}

}

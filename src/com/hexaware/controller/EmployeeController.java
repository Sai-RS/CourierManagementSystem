package com.hexaware.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.hexaware.dao.CourierServiceDb;
import com.hexaware.entity.Employee;


public class EmployeeController {

	
	public Employee login() throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		CourierServiceDb csd = new CourierServiceDb();

		String username,email;
		Employee employee;
		
		do {
			System.out.println("Enter your username: ");
			username = bf.readLine();
			System.out.println("Enter your E-mail: ");
			email = bf.readLine();
			employee = csd.validateEmployee(username, email);
			
		}while(employee.getEmployeeId() == 0);
		
		return employee;
	}
	
	public void displayAssignedOrders(ArrayList<ArrayList<String>> orders) {
		System.out.format("%-5s %-15s %-15s %-15s %-18s %-13s %s\n","Id","SenderName","ReceiverName","TrackingNumber","Status","DeliveryDate","Amount");
		System.out.println("\n");
		
		for(ArrayList<String> order:orders) {
			System.out.format("%-5s %-15s %-15s %-15s %-18s %-13s %s\n",order.get(0),order.get(1),order.get(2),order.get(3),order.get(4),order.get(5),order.get(6));
		}
	}

}


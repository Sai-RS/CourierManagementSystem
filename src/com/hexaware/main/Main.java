package com.hexaware.main;

import java.util.*;
import java.io.*;

import com.hexaware.controller.EmployeeController;
import com.hexaware.controller.UserController;
import com.hexaware.dao.CourierAdminServiceImpl;
import com.hexaware.dao.CourierServiceDb;
import com.hexaware.dao.CourierUserServiceImpl;
import com.hexaware.entity.Courier;
import com.hexaware.entity.Employee;
import com.hexaware.entity.User;

public class Main {

	public static void main(String[] args) throws IOException {
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		UserController uc = new UserController();
		EmployeeController ec = new EmployeeController();
		CourierUserServiceImpl cusi = new CourierUserServiceImpl();
		CourierAdminServiceImpl casi = new CourierAdminServiceImpl();
		CourierServiceDb csd = new CourierServiceDb();
		
		int choice;
		boolean flag = false;
		System.out.println("Are You a Customer/Employee ?\nPress\n\t1 for Employee\n\t2 for Customer");
		System.out.println("Enter your choice: ");
		choice = Integer.parseInt(bf.readLine());
		
		do {
			if(choice==1 || choice==2) {
				flag = true;
				
				if(choice==1) {
					System.out.println("\nSign In\n");
					Employee employee = ec.login();
					
					System.out.println("\nWelcome Back, "+employee.getEmployeeName()+"\n");
					
					if(employee.getRole().equals("Manager")) {
						int employeeChoice;
						
						do {
						
						System.out.println("Select your Preference:");
						System.out.println("Press\n\t1.Add Courier Staff\n\t2.LogOut");
						System.out.println("Enter your choice: ");
						employeeChoice = Integer.parseInt(bf.readLine());
						
						if(employeeChoice == 1) {
							boolean employeeFlag;
							do {
								employeeFlag = casi.addCourierStaff();
							}while(employeeFlag==false);
							
							if(employeeFlag) {
								System.out.println("Employee added successfully!!!\n\n");
							}
							
						}
						else if(employeeChoice==2) {
							System.out.println("Logged Out successfully!!!");
						}
						else {
							System.out.println("\nEnter a valid choice\n");
						}
						
						}while(employeeChoice!=2);
					}
					else {
						cusi.getAssignedOrder(employee.getEmployeeId());
					}
				}
				else {
					int customerChoice;
					boolean loginFlag = false;;
					System.out.println("Select your Preference:");
					System.out.println("Press\n\t1.Sign Up\n\t2.Sign In");
					System.out.println("Enter your choice: ");
					customerChoice = Integer.parseInt(bf.readLine());
					
					
					if(customerChoice == 1) {
						
						do {
							loginFlag = uc.createNewUser();
						}while(loginFlag==false);
						
						if(loginFlag == true) {
							System.out.println("User created successfully!!!!!!!!!!!\n");
							customerChoice = 2;
							
						}

						
					}
					if(customerChoice == 2) {
						User user = uc.login();
						System.out.println("Logged In\nWelcome Back, "+user.getUserName());
						
						int orderChoice;
						
						do {
							System.out.println("\nSelect your preference:");
							System.out.println("Press\n\t1.Make an Order\n\t2.Display Orders\n\t3.LogOut\n");
							System.out.println("Enter your choice:");
							orderChoice = Integer.parseInt(bf.readLine());
							
							if(orderChoice == 1) {
								Courier c = new Courier();
								c.setUser(user);
								String trackingNumber = cusi.placeOrder(uc.createCourier(c));
								if(trackingNumber!=null) {
									System.out.println("\nParcel Category: "+uc.getParcelCategory(trackingNumber));
									System.out.println("Tracking Number: "+trackingNumber);
									System.out.println("Status: "+cusi.getOrderStatus(trackingNumber));
									System.out.println("\n\n");
								}
							}
							else if(orderChoice == 2) {
								ArrayList<ArrayList<String>> orderDetailsArr = csd.getOrders(user);
								if(orderDetailsArr.size()>0) {
									System.out.format("%-5s %-15s %-15s %-15s %-18s %-13s %s\n","Id","SenderName","ReceiverName","TrackingNumber","Status","DeliveryDate","Amount");
									System.out.println("\n");
									
									for(ArrayList<String> oda:orderDetailsArr) {
										System.out.format("%-5s %-15s %-15s %-15s %-18s %-13s %s\n",oda.get(0),oda.get(1),oda.get(2),oda.get(3),oda.get(4),oda.get(5),oda.get(6));
									}
									
									int orderCancelChoice;
									do {
									System.out.println("\nSelect your preference:");
									System.out.println("Press\n\t1.Cancel an Order\n\t2.Return Back\n\n");
									System.out.println("Enter your choice:");
									
									orderCancelChoice = Integer.parseInt(bf.readLine());
									
									if(orderCancelChoice == 1) {
										System.out.println("Select the order to be deleted!\n");
										
										
										for(ArrayList<String> oda:orderDetailsArr) {
											System.out.format("Press %d for", orderDetailsArr.indexOf(oda)+1);
											System.out.println("\n");
											System.out.format("%-5s %-15s %-15s %-15s %-18s %-13s %s\n","Id","SenderName","ReceiverName","TrackingNumber","Status","DeliveryDate","Amount");
											
											System.out.format("%-5s %-15s %-15s %-15s %-18s %-13s %s\n",oda.get(0),oda.get(1),oda.get(2),oda.get(3),oda.get(4),oda.get(5),oda.get(6));
										}
										
										System.out.println("Enter your choice: ");
										int orderNumber = Integer.parseInt(bf.readLine())-1;
										
										if(cusi.cancelOrder(orderDetailsArr.get(orderNumber).get(3))) {
											orderDetailsArr.remove(orderNumber);
											System.out.println("Your order has cancelled.");
											System.out.println("Your payment will be refunded in 5 days.\n");
										}
									}
									else if(orderCancelChoice == 2) {
										break;
									}
									else {
										System.out.println("Enter a valid choice number\n");
									}
				
									
									}while(orderDetailsArr.size()>0);
								}
								else {
									System.out.println("No Orders have been made so far.\n\n");
								}
							}
							else if(orderChoice == 3) {
								System.out.println("Logged Out");
							}
							else {
								System.out.println("Enter a valid choice");
							}
							
						}while(orderChoice != 3);
				
						
					}
				}
			}
			else {
				System.out.println("Choice invalid");
				System.out.println("Re-enter your choice: ");
				choice = Integer.parseInt(bf.readLine());
			}
			
		}while(flag == false);
		
	}

}

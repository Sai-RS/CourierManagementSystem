package com.hexaware.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hexaware.dao.CourierServiceDb;
import com.hexaware.entity.*;

public class UserController {
	
	public boolean createNewUser() throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		CourierServiceDb csd = new CourierServiceDb();
		
		User user = new User();
		String name,email,contactNumber;
		
		// Name
		
		System.out.println("\nEnter your Name: ");
		name = bf.readLine();
		
		while(!CustomerDataValidation.verifyName(name)) {
			System.out.println("The Name should contain only alphabetical letters.\n");
			System.out.println("Re-Enter your name: ");
			name = bf.readLine();
		}	
		user.setUserName(name);
		
		// Email
		
		System.out.println("\nEnter your Email: ");
		email = bf.readLine();
		
		while(!CustomerDataValidation.verifyEmail(email)) {
			System.out.println("The given email is invalid.\n");
			System.out.println("Re-Enter your email: ");
			email = bf.readLine();
		}	
		user.setEmail(CustomerDataValidation.unCapitalize(email));
		
		// Password
		
		System.out.println("\nEnter your password: ");
		user.setPassword(bf.readLine());
		
		// Contact Number
		
		System.out.println("\nEnter your Contact Number: ");
		contactNumber = bf.readLine();
		
		while(!CustomerDataValidation.verifyContactNumber(contactNumber)) {
			System.out.println("Please enter your contact number in ###-###-#### format.\n");
			System.out.println("Re-Enter your Contact Number: ");
			contactNumber = bf.readLine();
		}	
		user.setContactNumber(contactNumber);
		
		// Address
		
		System.out.println("\nEnter your address:\n");
		user.setAddress(CustomerDataValidation.getAddress());
		
		
	    return csd.insertUserDetails(user);	
		
	}
	
	public User login() throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		CourierServiceDb csd = new CourierServiceDb();

		String username,password;
		User user;
		
		do {
			System.out.println("Enter your username: ");
			username = bf.readLine();
			System.out.println("Enter your password: ");
			password = bf.readLine();
			user = csd.validateUser(username, password);
			
		}while(user.getUserId() == 0);
		
		return user;
		
	}
	
	public Courier createCourier(Courier courier) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		CourierServiceDb csd = new CourierServiceDb();
		
		System.out.println("Enter your Name: ");
		courier.setSenderName(bf.readLine());
		
		System.out.println("Sender Address Details:-");
		String senderAddress = CustomerDataValidation.getAddress();
		
		while(!csd.checkLocationAvailability(senderAddress)) {
			System.out.println("The service is not available for the given city");
			System.out.println("Please provide a nearest location: ");
			senderAddress = CustomerDataValidation.getAddress();
		}
		courier.setSenderAddress(senderAddress);
		
		System.out.println("Enter your Receiver's Name: ");
		courier.setReceiverName(bf.readLine());
		
		System.out.println("Receiver Address Details:-");
		String receiverAddress = CustomerDataValidation.getAddress();
		
		while(!csd.checkLocationAvailability(receiverAddress)) {
			System.out.println("The service is not available for the given city");
			System.out.println("Please provide a nearest location: ");
			receiverAddress = CustomerDataValidation.getAddress();
		}
		courier.setReceiverAddress(receiverAddress);
		
		
		System.out.println("Enter your weight: ");
		courier.setWeight(Double.parseDouble(bf.readLine()));
		
		courier.setStatus("Payment Pending");
		
		Random rn = new Random();
		String trackingNumber = String.valueOf(rn.nextInt(999) + new Date().getTime());
		
		courier.setTrackingNumber(trackingNumber);
		//courier.setDeliveryDate(new Date());
		
		return courier;
	}
	
	public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double R = 6371;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return Math.round(R * c);
    }

	
	public boolean makePayment(Payment payment,Courier courier,Location senderLocation,Location receiverLocation,double serviceCost){
		
		double distance = UserController.calculateDistance(senderLocation.getLatitude(), senderLocation.getLongitude(),
				receiverLocation.getLatitude(), receiverLocation.getLongitude());
		
		double shippingCosts = (distance * serviceCost) * courier.getWeight();
		
		payment.setAmount(shippingCosts);
		payment.setCourier(courier);
		
		Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(today);
        
		payment.setPaymentDate(date);
		payment.setLocation(senderLocation);
		
		int choice;
		Scanner sc = new Scanner(System.in);

		System.out.println("The Total Amount is "+shippingCosts);
		System.out.println("Press\n\t1. Make Payment\n\t2. Cancel");
		
		choice = sc.nextInt();
		
		if(choice==1) {
			System.out.println("Payment made successfully!!!");
			return true;
		}
		return false;
		
	}
	
	public String getParcelCategory(String trackingNumber) {
		CourierServiceDb csd = new CourierServiceDb();
		String category ;
		int weight = (int)csd.getCourierWeight(trackingNumber);
		
		switch((weight<=10)? 1 : (weight<=15) ? 2 : (weight>15) ? 3 : 4) {
		case 1:
			category = "Light";
			break;
		case 2:
			category = "Medium";
			break;
		case 3:
			category = "Heavy";
			break;
		default:
			category = "";
		}
		
		return category;
	}

}

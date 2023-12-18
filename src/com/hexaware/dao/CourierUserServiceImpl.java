package com.hexaware.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.hexaware.controller.EmployeeController;
import com.hexaware.controller.UserController;
import com.hexaware.entity.*;

public class CourierUserServiceImpl implements ICourierUserService{

	@Override
	public String placeOrder(Courier courier) {
		
		CourierServiceDb csd = new CourierServiceDb();
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		UserController uc = new UserController();
		
		Order order = new Order();
		
		// Courier
		order.setCourier(courier);
		
		// Sender
		order.setSender(courier.getUser());
		
		// OrderDate
		
		Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(today);
        
        order.setOrderDate(date);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        int numberOfDaysToAdd = 5;
        calendar.add(Calendar.DAY_OF_MONTH, numberOfDaysToAdd);
        Date newDate = calendar.getTime();
        String deliveryDate = sdf.format(newDate);
		
        // SenderLocation
        
        Location senderLocation = csd.getLocation(courier.getSenderAddress());
		order.setSenderLocation(senderLocation);
		
		// ReceiverLocation
		
		Location receiverLocation = csd.getLocation(courier.getReceiverAddress());
		order.setReceiverLocation(receiverLocation);
		
		// CourierService
		
		int choice=1;
		ArrayList<CourierService> csArr = csd.getCourierServices();
		System.out.println("Select a courier service:\nPress\n");
		
		for(CourierService cs:csArr) {
			System.out.format("\t%d. %s",cs.getCourierServiceId(),cs.getServiceName() );
			System.out.println("\n");
		}
		System.out.println("Enter your choice: ");
		try {
			choice = Integer.parseInt(bf.readLine());
		} 
		catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		} 
		
		order.setService(csArr.get(choice-1));
		
		// Employee
		order.setEmployee(csd.getEmployee());
		
		// Payment
		
		Payment payment = new Payment();
		
		boolean flag = uc.makePayment(payment,courier,senderLocation,receiverLocation,order.getService().getCost());
			
		
		if(!flag){
				do {
					System.out.println("Payment must be made to place the order");
					System.out.println("Press\n\t1. Make Payment\n\t2. Cancel Order");
					System.out.println("Enter your choice: ");
					choice = sc.nextInt();
					if(choice == 1) 
						flag = uc.makePayment(payment,courier,senderLocation,receiverLocation,order.getService().getCost());
					else
						break;
				}while(choice==1 && flag==false);
		}
		if(flag) {
				order.setPayment(payment);
				
				courier.setStatus("Yet to transit");
				courier.setDeliveryDate(deliveryDate);
				long courierId = csd.insertCourierDetails(courier);
				courier.setCourierId(courierId);
				
				long paymentId = csd.insertPaymentDetails(payment);
				payment.setPaymentId(paymentId);
				
				boolean orderFlag = csd.insertOrderDetails(order);
				
				if(orderFlag) {
					System.out.println("Your Order has been made successfully");
				}
				return courier.getTrackingNumber();
				
		}
		else {	
				System.out.println("Your Order has been cancelled");
				return null;
		}	
		
	}

	@Override
	public String getOrderStatus(String trackingNumber) {
		CourierServiceDb csd = new CourierServiceDb();

		String status = csd.getOrderStatus(trackingNumber);
		return status;
		
	}

	@Override
	public boolean cancelOrder(String trackingNumber) {
		CourierServiceDb csd = new CourierServiceDb();
		return csd.cancelOrder(trackingNumber);
		
		
	}

	@Override
	public void getAssignedOrder(long employeeId) {
		CourierServiceDb csd = new CourierServiceDb();
		ArrayList<ArrayList<String>> orders= csd.getAssignedOrders(employeeId);
		
		if(orders.size()==0) {
			System.out.println("No orders has been assigned to you yet!!!");
		}
		else {
			EmployeeController ec = new EmployeeController();
			ec.displayAssignedOrders(orders);
		}
	}
	

}

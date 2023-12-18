package com.hexaware.dao;

import com.hexaware.entity.Courier;

public interface ICourierUserService {
	String placeOrder(Courier courier);
	String getOrderStatus(String trackingNumber);
	boolean cancelOrder(String trackingNumber);
	void getAssignedOrder(long employeeId);

}

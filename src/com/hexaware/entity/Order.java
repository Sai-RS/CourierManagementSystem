package com.hexaware.entity;

public class Order {
	private long orderId;
	private User sender;
	private String orderDate;
	private Courier courier;
	private CourierService service;
	private Employee employee;
	private Location senderLocation;
	private Location receiverLocation;
	private Payment payment;
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public Courier getCourier() {
		return courier;
	}
	public void setCourier(Courier courier) {
		this.courier = courier;
	}
	public CourierService getService() {
		return service;
	}
	public void setService(CourierService service) {
		this.service = service;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Location getSenderLocation() {
		return senderLocation;
	}
	public void setSenderLocation(Location senderLocation) {
		this.senderLocation = senderLocation;
	}
	public Location getReceiverLocation() {
		return receiverLocation;
	}
	public void setReceiverLocation(Location receiverLocation) {
		this.receiverLocation = receiverLocation;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
	
	

}

package com.hexaware.entity;

public class CourierService {
	
	private long courierServiceId;
	private String serviceName;
	private double cost;

	public CourierService(long courierServiceId, String serviceName, double cost) {
		this.courierServiceId = courierServiceId;
		this.serviceName = serviceName;
		this.cost = cost;
	}

	public long getCourierServiceId() {
		return courierServiceId;
	}

	public void setCourierServiceId(long courierServiceId) {
		this.courierServiceId = courierServiceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	

}

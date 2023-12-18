package com.hexaware.entity;

import java.util.ArrayList;

public class CourierCompany {

	
	private String companyName;
	private ArrayList<Courier> couriers;
	private ArrayList<Employee> employees;
	private ArrayList<Location> locations;
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public ArrayList<Courier> getCouriers() {
		return couriers;
	}
	public void setCouriers(ArrayList<Courier> couriers) {
		this.couriers = couriers;
	}
	public ArrayList<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}
	public ArrayList<Location> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}
	
	@Override
	public String toString() {
		return "CourierCompany [companyName=" + companyName + ", couriers=" + couriers + ", employees=" + employees
				+ ", locations=" + locations + ", toString()=" + super.toString() + "]";
	}
	
	
}

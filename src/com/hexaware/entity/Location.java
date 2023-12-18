package com.hexaware.entity;

public class Location {

	// LocationID , LocationName , Address
	private long locationId;
	private String locationName;
	private String address;
	private double latitude;
	private double longitude;
	
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", locationName=" + locationName + ", address=" + address
				+ ", toString()=" + super.toString() + "]";
	}
	
}

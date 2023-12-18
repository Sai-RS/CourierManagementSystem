package com.hexaware.exception;

public class TrackingNumberNotFoundException extends Exception{
	
	public String toString() {
		return "The Given Tracking Number is invalid.";
	}
}

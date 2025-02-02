package com.bhaskar.response;

public class ApiResponse {

	private String message;
	private boolean status;
	
	public ApiResponse(String message, boolean status) {
		// TODO Auto-generated constructor stub
		
		this.message = message;
		this.status = status;
	}
public ApiResponse() {
		
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}

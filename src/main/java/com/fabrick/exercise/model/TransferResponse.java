package com.fabrick.exercise.model;

public class TransferResponse {
	
	private String code;
	private String description;
	
	public TransferResponse() {
		
	}
	
	public TransferResponse(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}

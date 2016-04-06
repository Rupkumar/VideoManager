package com.videomanager.model;

public class GetUserVideoResponse {

	private byte[] data;
	
	public GetUserVideoResponse(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}

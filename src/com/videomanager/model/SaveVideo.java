package com.videomanager.model;

import java.util.Arrays;

public class SaveVideo {
	
	private String userName;
	
	private String fileName;
	
	private byte[] data;
	
	private boolean usePrivate;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public boolean isUsePrivate() {
		return usePrivate;
	}

	public void setUsePrivate(boolean usePrivate) {
		this.usePrivate = usePrivate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + (usePrivate ? 1231 : 1237);
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaveVideo other = (SaveVideo) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (usePrivate != other.usePrivate)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SaveVideo [userName=" + userName + ", fileName=" + fileName
				+ ", data=" + Arrays.toString(data) + ", usePrivate="
				+ usePrivate + "]";
	}
	
	

}

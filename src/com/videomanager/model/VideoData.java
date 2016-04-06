package com.videomanager.model;

import java.util.Date;

public class VideoData {

	private String userName;
	
	private String fileName;
	
	private String lastUpdated;
	
	private String localVideoFileName;
	
	public VideoData() {
	}
	
	public VideoData(String userName, String fileName, String lastUpdated, String localVideoFileName) {
		this.userName = userName;
		this.fileName = fileName;
		this.lastUpdated = lastUpdated;
		this.localVideoFileName = localVideoFileName;
	}

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

	public String getLastUpdated() {
		return lastUpdated;
	}

	public String getLocalVideoFileName() {
		return localVideoFileName;
	}

	public void setLocalVideoFileName(String localVideoFileName) {
		this.localVideoFileName = localVideoFileName;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime
				* result
				+ ((localVideoFileName == null) ? 0 : localVideoFileName
						.hashCode());
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
		VideoData other = (VideoData) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (lastUpdated == null) {
			if (other.lastUpdated != null)
				return false;
		} else if (!lastUpdated.equals(other.lastUpdated))
			return false;
		if (localVideoFileName == null) {
			if (other.localVideoFileName != null)
				return false;
		} else if (!localVideoFileName.equals(other.localVideoFileName))
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
		return "VideoData [userName=" + userName + ", fileName=" + fileName
				+ ", lastUpdated=" + lastUpdated + ", localVideoFileName="
				+ localVideoFileName + "]";
	}
	
	
	
}

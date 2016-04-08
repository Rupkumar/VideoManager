package com.videomanager.model;


public class VideoData {

	private String userName;
	
	private boolean privateFlag;
	
	private String fileName;
	
	private String contenType;
	
	private String lastUpdated;
	
	private String localVideoFileName;
	
	public VideoData() {
	}
	
	public VideoData(String userName, boolean privateFlag, String fileName, String contentType, String lastUpdated, String localVideoFileName) {
		this.userName = userName;
		this.privateFlag = privateFlag;
		this.fileName = fileName;
		this.contenType = contentType;
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

	public boolean isPrivateFlag() {
		return privateFlag;
	}

	public void setPrivateFlag(boolean privateFlag) {
		this.privateFlag = privateFlag;
	}

	public String getContenType() {
		return contenType;
	}

	public void setContenType(String contenType) {
		this.contenType = contenType;
	}

	@Override
	public String toString() {
		return "VideoData [userName=" + userName + ", privateFlag=" + privateFlag + ", fileName=" + fileName
				+ ", contenType=" + contenType + ", lastUpdated=" + lastUpdated + ", localVideoFileName="
				+ localVideoFileName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contenType == null) ? 0 : contenType.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime * result + ((localVideoFileName == null) ? 0 : localVideoFileName.hashCode());
		result = prime * result + (privateFlag ? 1231 : 1237);
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		if (contenType == null) {
			if (other.contenType != null)
				return false;
		} else if (!contenType.equals(other.contenType))
			return false;
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
		if (privateFlag != other.privateFlag)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
}

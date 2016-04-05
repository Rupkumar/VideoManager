package com.videomanager.model;

import java.util.List;

public class GetVideoResponse {
	private List<VideoData> videoList;
	 private Long responseTime;
	 
	 public GetVideoResponse(List<VideoData> videoList, Long timeInMillSeconds) {
		 this.videoList = videoList;
		 this.responseTime = timeInMillSeconds;
	 }
	 
	 public List<VideoData> getVideoList() {
		 return videoList;
	 }
	 
	 public Long getResponseTime() {
		 return responseTime;
	 }
	
	@Override
	public String toString() {
		return "GetVideoResponse [videoList=" + videoList + ", responseTime="
				+ responseTime + "]";
	}
	 
 
}

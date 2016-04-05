package com.videomanager.model;

import java.util.List;

public class GetVideoResponse {
	private List<VideoData> videos;
	 private Long responseTime;
	 
	 public GetVideoResponse(List<VideoData> videos, Long timeInMillSeconds) {
		 this.videos = videos;
		 this.responseTime = timeInMillSeconds;
	 }
	 
	 public List<VideoData> getVideos() {
		 return videos;
	 }
	 
	 public Long getResponseTime() {
		 return responseTime;
	 }
	 
	public void setVideos(List<VideoData> videos) {
		this.videos = videos;
	}

	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "GetVideoResponse [videos=" + videos + ", responseTime="
				+ responseTime + "]";
	}
	 
 
}

package com.videomanager.mongodb;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.videomanager.model.GetUserVideoListResponse;
import com.videomanager.model.GetUserVideoResponse;
import com.videomanager.model.SaveVideo;
import com.videomanager.mongodb.dao.VideoManagerDAO;

@Component
public class SimpleMongoDBManager implements MongoDBManager {

	private VideoManagerDAO videoManagerDAO;
	
	@Override
	public GetUserVideoListResponse getVideoListForUser(String username, boolean inclVideo) {
		return videoManagerDAO.getVideoListForUser(username, inclVideo);
	}

	@Override
	public void saveVideoForUser(List<SaveVideo> videos) {
		if (videos != null && videos.isEmpty()) {
			return;
		}
		for (SaveVideo video : videos) {
			videoManagerDAO.saveVideoForUser(video.getUserName(), video.getFileName(), video.isUsePrivate(), video.getData());
		}
		
	}
	
	@Override
	public GetUserVideoResponse getUserVideo(String videoFileName) throws IOException {
		return videoManagerDAO.getUserVideo(videoFileName);
	}
	
	
	@Autowired
	public void setVideoManagerDAO(VideoManagerDAO videoManagerDAO) {
		this.videoManagerDAO = videoManagerDAO;
	}

}

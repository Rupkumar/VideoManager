package com.videomanager.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.videomanager.model.GetVideoResponse;
import com.videomanager.model.SaveVideo;
import com.videomanager.model.VideoData;
import com.videomanager.mongodb.dao.VideoManagerDAO;

@Component
public class SimpleMongoDBManager implements MongoDBManager {

	private VideoManagerDAO videoManagerDAO;
	
	@Override
	public GetVideoResponse getVideoListForUser(String username) {
		return videoManagerDAO.getVideoListForUser(username);
	}

	@Override
	public void saveVideoForUser(List<SaveVideo> videos) {
		if (videos != null && videos.isEmpty()) {
			return;
		}
		for (SaveVideo video : videos) {
			videoManagerDAO.saveVideoForUser(video.getUserName(), video.getFileName(), true, video.getData());
		}
		
	}
	
	
	@Autowired
	public void setVideoManagerDAO(VideoManagerDAO videoManagerDAO) {
		this.videoManagerDAO = videoManagerDAO;
	}

}

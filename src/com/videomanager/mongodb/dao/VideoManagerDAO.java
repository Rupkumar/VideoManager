package com.videomanager.mongodb.dao;

import java.util.List;

import com.videomanager.model.GetVideoResponse;
import com.videomanager.model.VideoData;


public interface VideoManagerDAO {

	void saveVideoForUser(String userName, String fileName, boolean privateFlag, byte[] video);
	
	GetVideoResponse getVideoListForUser(String userName);
}

package com.videomanager.mongodb.dao;

import java.io.IOException;

import com.videomanager.model.GetUserVideoListResponse;
import com.videomanager.model.GetUserVideoResponse;


public interface VideoManagerDAO {

	void saveVideoForUser(String userName, String fileName, boolean privateFlag, byte[] video);
	
	GetUserVideoListResponse getVideoListForUser(String userName, boolean inclVideo);
	
	GetUserVideoResponse getUserVideo(String videoFileName) throws IOException;
}

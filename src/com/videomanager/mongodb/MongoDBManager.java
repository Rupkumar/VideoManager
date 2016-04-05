package com.videomanager.mongodb;

import java.util.List;

import com.videomanager.model.GetVideoResponse;
import com.videomanager.model.SaveVideo;

public interface MongoDBManager {

	public GetVideoResponse getVideoListForUser(String username);
	
	public void saveVideoForUser(List<SaveVideo> videos);
}

package com.videomanager.mongodb;

import java.util.List;

import com.videomanager.model.SaveVideo;
import com.videomanager.model.VideoList;

public interface MongoDBManager {

	public List<VideoList> getVideoListForUser(String username);
	
	public void saveVideoForUser(List<SaveVideo> videos);
}

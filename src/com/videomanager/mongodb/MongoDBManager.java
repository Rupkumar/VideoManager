package com.videomanager.mongodb;

import java.io.IOException;
import java.util.List;

import com.videomanager.model.GetUserVideoListResponse;
import com.videomanager.model.GetUserVideoResponse;
import com.videomanager.model.SaveVideo;

public interface MongoDBManager {

	public GetUserVideoListResponse getVideoListForUser(String username);
	
	public void saveVideoForUser(List<SaveVideo> videos);
	
	public GetUserVideoResponse getUserVideo(String videoFileName) throws IOException;
}

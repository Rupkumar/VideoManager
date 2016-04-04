package com.videomanager.mongodb.dao;

import java.util.List;

import com.videomanager.model.VideoList;


public interface VideoManagerDAO {

	void saveVideoForUser(String userName, String fileName, boolean privateFlag, byte[] video);
	
	List<VideoList> getVideoListForUser(String userName);
}

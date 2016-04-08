package com.videomanager.mongodb.dao;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.videomanager.model.GetUserVideoListResponse;


public interface VideoManagerDAO {

	void saveVideoForUser(String userName, boolean privateFlag, MultipartFile file) throws IOException;
	
	GetUserVideoListResponse getVideoListForUser(String userName, boolean includeOthersPublic);
	
	GridFSDBFile getVideoFile(String videoFileName);
}

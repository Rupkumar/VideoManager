package com.videomanager.mongodb.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.videomanager.model.GetVideoResponse;
import com.videomanager.model.VideoData;
import com.videomanager.mongodb.dao.VideoManagerDAO;

public class VideoManagerDAOImpl implements VideoManagerDAO {

	private DB mongoDB;

	@Override
	public void saveVideoForUser(String userName, String fileName,boolean privateFlag, byte[] video) {
		DBCollection col = mongoDB.getCollection("userVideoList");
		BasicDBObject document = new BasicDBObject();
		document.put("userName", userName);
		document.put("fileName", fileName);
		document.put("createdDate", new Date());
		document.put("privateFlag", privateFlag ? "Y" : "N");
		col.insert(document);
		
		GridFS gfsText = new GridFS(mongoDB, "userVideos");
    	GridFSInputFile gfsFile = gfsText.createFile(video);
    	gfsFile.setFilename(fileName);
    	gfsFile.save();
    	System.out.println("Saved");
	}

	@Override
	public GetVideoResponse getVideoListForUser(String userName) {
		return null;
	}
	
	
	@Autowired
	public void setMongoDB(DB mongoDB) {
		this.mongoDB = mongoDB;
	}
}

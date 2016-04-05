package com.videomanager.mongodb.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.videomanager.model.GetVideoResponse;
import com.videomanager.model.VideoData;
import com.videomanager.mongodb.dao.VideoManagerDAO;
import com.videomanager.web.controllers.RestAPIController;

public class VideoManagerDAOImpl implements VideoManagerDAO {
	
	private static final Log LOG = LogFactory.getLog(VideoManagerDAOImpl.class);

	private DB mongoDB;

	@Override
	public void saveVideoForUser(String userName, String fileName,boolean privateFlag, byte[] video) {
		DBCollection table = mongoDB.getCollection("userVideoList");
		BasicDBObject document = new BasicDBObject();
		document.put("userName", userName);
		document.put("fileName", fileName);
		document.put("createdOn", new Date());
		document.put("privateFlag", privateFlag ? "Y" : "N");
		table.insert(document);
		
		GridFS gfsText = new GridFS(mongoDB, "userVideos");
    	GridFSInputFile gfsFile = gfsText.createFile(video);
    	gfsFile.setFilename(fileName);
    	gfsFile.save();
    	System.out.println("Saved");
	}

	@Override
	public GetVideoResponse getVideoListForUser(String userName) {
		LOG.info("userName = " + userName);

		if (userName == null || userName.isEmpty()) {
			return new GetVideoResponse(null, 0L);
		}
		
		long start = new Date().getTime();
		
		Set<VideoData> videos = new HashSet<VideoData>();

		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("userName", userName);
		videos.addAll(queryVideoData(searchQuery1));
		BasicDBObject searchQuery2 = new BasicDBObject();
		searchQuery2.put("privateFlag", "N");
		videos.addAll(queryVideoData(searchQuery2));
		
		long end = new Date().getTime() - start;
		
		return new GetVideoResponse(new ArrayList<VideoData>(videos), end);
		//return mockedData;
	}
	
	private Set<VideoData> queryVideoData(BasicDBObject searchQuery) {
		Set<VideoData> videos = new HashSet<VideoData>();
		DBCollection table = mongoDB.getCollection("userVideoList");
		DBCursor cursor = table.find(searchQuery);
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			LOG.info("document = " + document);
			VideoData data = new VideoData(document.getString("userName"), document.getString("fileName"), document.getDate("createdOn").toString());
			videos.add(data);
		}
		return videos;
	}
	
	private GetVideoResponse mockedData() {
		List<VideoData> list = new ArrayList<VideoData>();
		for (int i=0; i< 10; i++) {
			VideoData data = new VideoData();
			data.setFileName("Video " + i);
			data.setLastUpdated(new Date().toString());
			data.setUserName("rupkumar");
			list.add(data);
		}
		return new GetVideoResponse(list, 2L);
	}
	
	
	@Autowired
	public void setMongoDB(DB mongoDB) {
		this.mongoDB = mongoDB;
	}
}

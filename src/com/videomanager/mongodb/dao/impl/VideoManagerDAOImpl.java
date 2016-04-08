package com.videomanager.mongodb.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.videomanager.model.GetUserVideoListResponse;
import com.videomanager.model.VideoData;
import com.videomanager.mongodb.dao.VideoManagerDAO;

public class VideoManagerDAOImpl implements VideoManagerDAO {
	
	private static final Log LOG = LogFactory.getLog(VideoManagerDAOImpl.class);

	private DB mongoDB;

	@Override
	public void saveVideoForUser(String userName, boolean privateFlag, MultipartFile file) throws IOException {
		DBCollection table = mongoDB.getCollection("userVideoList");
		
		String localVideoFileName = UUID.randomUUID().toString();
		BasicDBObject document = new BasicDBObject();
		document.put("userName", userName);
		document.put("privateFlag", privateFlag ? "Y" : "N");
		document.put("fileName", file.getOriginalFilename());
		document.put("contentType", file.getContentType());
		document.put("uploadedDate", new Date());
		document.put("videoFileName", localVideoFileName);
		table.insert(document);
		
		GridFS gfsText = new GridFS(mongoDB, "userVideos");
    	GridFSInputFile gfsFile = gfsText.createFile(file.getBytes());
    	gfsFile.setFilename(localVideoFileName);
    	gfsFile.save();
    	LOG.info("Saved filename : " + file.getOriginalFilename() + " , localFileName: " + localVideoFileName);
	}

	@Override
	public GetUserVideoListResponse getVideoListForUser(String userName, boolean includeOthersPublic) {
		LOG.info("userName = " + userName);

		if (userName == null || userName.isEmpty()) {
			return new GetUserVideoListResponse(null, 0L);
		}
		
		long start = new Date().getTime();

		Set<VideoData> videos = new HashSet<VideoData>();

		BasicDBObject searchQuery1 = new BasicDBObject();
		searchQuery1.put("userName", userName);
		videos.addAll(queryVideoData(searchQuery1));
		if (includeOthersPublic) {
			BasicDBObject searchQuery2 = new BasicDBObject();
			searchQuery2.put("privateFlag", "N");
			videos.addAll(queryVideoData(searchQuery2));
		}
		long end = new Date().getTime() - start;
		
		return new GetUserVideoListResponse(new ArrayList<VideoData>(videos), end);
	}
	
	private Set<VideoData> queryVideoData(BasicDBObject searchQuery) {
		Set<VideoData> videos = new HashSet<VideoData>();
		DBCollection table = mongoDB.getCollection("userVideoList");
		DBCursor cursor = table.find(searchQuery);
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			LOG.info("document = " + document);
			VideoData video = new VideoData();
			video.setUserName(document.getString("userName"));
			video.setPrivateFlag(document.getString("privateFlag").equals("Y")? true : false);
			video.setFileName(document.getString("fileName"));
			video.setContenType(document.getString("contentType"));
			video.setLastUpdated(document.getDate("uploadedDate").toString());
			video.setLocalVideoFileName(document.getString("videoFileName"));
			videos.add(video);
		}
		return videos;
	}
	
	@Override
	public GridFSDBFile getVideoFile(String videoFileName) {
		GridFS gfsText = new GridFS(mongoDB, "userVideos");
    	GridFSDBFile videoFile = gfsText.findOne(videoFileName);
    	return videoFile;
	}

	@Autowired
	public void setMongoDB(DB mongoDB) {
		this.mongoDB = mongoDB;
	}
}

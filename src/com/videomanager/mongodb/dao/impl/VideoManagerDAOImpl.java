package com.videomanager.mongodb.dao.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.videomanager.model.GetUserVideoListResponse;
import com.videomanager.model.GetUserVideoResponse;
import com.videomanager.model.VideoData;
import com.videomanager.mongodb.dao.VideoManagerDAO;

public class VideoManagerDAOImpl implements VideoManagerDAO {
	
	private static final Log LOG = LogFactory.getLog(VideoManagerDAOImpl.class);

	private DB mongoDB;

	@Override
	public void saveVideoForUser(String userName, String fileName,boolean privateFlag, byte[] video) {
		DBCollection table = mongoDB.getCollection("userVideoList");
		
		String localVideoFileName = UUID.randomUUID().toString();
		BasicDBObject document = new BasicDBObject();
		document.put("userName", userName);
		document.put("fileName", fileName);
		document.put("createdOn", new Date());
		document.put("privateFlag", privateFlag ? "Y" : "N");
		document.put("videoFileName", localVideoFileName);
		table.insert(document);
		
		GridFS gfsText = new GridFS(mongoDB, "userVideos");
    	GridFSInputFile gfsFile = gfsText.createFile(video);
    	gfsFile.setFilename(localVideoFileName);
    	gfsFile.save();
    	LOG.info("Saved " + localVideoFileName);
	}
	
	@Override
	public GetUserVideoResponse getUserVideo(String videoFileName) throws IOException {
    	return new GetUserVideoResponse(getVideo(videoFileName));
	}

	@Override
	public GetUserVideoListResponse getVideoListForUser(String userName, boolean inclVideo) {
		LOG.info("userName = " + userName);

		if (userName == null || userName.isEmpty()) {
			return new GetUserVideoListResponse(null, 0L);
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
		
		return new GetUserVideoListResponse(new ArrayList<VideoData>(videos), end);
		//return mockedData;
	}
	
	private Set<VideoData> queryVideoData(BasicDBObject searchQuery) {
		Set<VideoData> videos = new HashSet<VideoData>();
		DBCollection table = mongoDB.getCollection("userVideoList");
		DBCursor cursor = table.find(searchQuery);
		while (cursor.hasNext()) {
			BasicDBObject document = (BasicDBObject) cursor.next();
			LOG.info("document = " + document);
			String videoFileName = document.getString("videoFileName");
			if (videoFileName != null) {
				VideoData data = new VideoData(document.getString("userName"), document.getString("fileName"), document.getDate("createdOn").toString(), videoFileName);
				data.setVideoData(getVideo(videoFileName));
				videos.add(data);
			}
		}
		return videos;
	}
	
	private byte[] getVideo(String videoFileName) {
		GridFS gfsText = new GridFS(mongoDB, "userVideos");
    	GridFSDBFile imageForOutput = gfsText.findOne(videoFileName);
    	ByteArrayOutputStream output = new ByteArrayOutputStream();
    	try {
    		imageForOutput.writeTo(output);
    	} catch (Exception e) {
    		LOG.error("Failed to fetch the video " + e);
    	}
    	return output.toByteArray();
	}
	
	private GetUserVideoListResponse mockedData() {
		List<VideoData> list = new ArrayList<VideoData>();
		for (int i=0; i< 10; i++) {
			VideoData data = new VideoData();
			data.setFileName("Video " + i);
			data.setLastUpdated(new Date().toString());
			data.setUserName("rupkumar");
			list.add(data);
		}
		return new GetUserVideoListResponse(list, 2L);
	}
	
	
	@Autowired
	public void setMongoDB(DB mongoDB) {
		this.mongoDB = mongoDB;
	}
	
	public static void main(String... args) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB mongoDB = mongoClient.getDB("videodb");
		VideoManagerDAOImpl videoManagerDAOImpl = new VideoManagerDAOImpl();
		videoManagerDAOImpl.setMongoDB(mongoDB);
		try {
			FileInputStream videoFile = new FileInputStream("C:\\temp\\Test.mp4");
			byte[] data = new byte[videoFile.available()];
			videoFile.read(data);
			videoFile.close();
			videoManagerDAOImpl.saveVideoForUser("rupkumar", "TestVideo", false, data);
		} catch (Exception e) {
			System.out.print(e);
		} finally{
			mongoClient.close();
		}
	}
}

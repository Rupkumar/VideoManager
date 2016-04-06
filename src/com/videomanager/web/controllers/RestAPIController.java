package com.videomanager.web.controllers;

import java.util.Arrays;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.videomanager.model.GetUserVideoListResponse;
import com.videomanager.model.SaveVideo;
import com.videomanager.mongodb.MongoDBManager;

@RestController
public class RestAPIController {
	
	@Autowired
	private MongoDBManager mongoDBManager;

	private static final Log LOG = LogFactory.getLog(RestAPIController.class);

	@RequestMapping("/api/myvideos/{user}")
	public Callable<GetUserVideoListResponse> getUserVideos(@PathVariable String user) {
		return new Callable<GetUserVideoListResponse>() {
			@Override
			public GetUserVideoListResponse call() throws Exception {
				GetUserVideoListResponse response = mongoDBManager.getVideoListForUser(user);
				LOG.info("Recieved videoList from Services: " + response);
				return response;
			}
		};
	}
	
	@RequestMapping("/api/uploadvideos/")
	public Callable<GetUserVideoListResponse> uploadVideo(@RequestParam("file") MultipartFile file) {
		return new Callable<GetUserVideoListResponse>() {
			@Override
			public GetUserVideoListResponse call() throws Exception {
				if (file != null) {
					LOG.info("Recieved file : " + file.getOriginalFilename());
					LOG.info("Content Type : " + file.getContentType());
					SaveVideo saveVideo = new SaveVideo();
					saveVideo.setFileName(file.getOriginalFilename());
					saveVideo.setUserName("rupkumar");
					saveVideo.setData(file.getBytes());
					saveVideo.setUsePrivate(false);
					//mongoDBManager.saveVideoForUser(Arrays.asList(saveVideo));
					LOG.info("Saved the file " + file.getOriginalFilename());
				}
				return null;
			}
		};
	}

}

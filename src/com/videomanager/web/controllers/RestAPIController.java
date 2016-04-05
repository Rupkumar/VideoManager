package com.videomanager.web.controllers;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videomanager.model.GetVideoResponse;
import com.videomanager.mongodb.MongoDBManager;

@RestController
public class RestAPIController {
	
	@Autowired
	private MongoDBManager mongoDBManager;

	private static final Log LOG = LogFactory.getLog(RestAPIController.class);

	@RequestMapping("/api/myvideos/{user}")
	public Callable<GetVideoResponse> getUserVideos(@PathVariable String user) {
		return new Callable<GetVideoResponse>() {
			@Override
			public GetVideoResponse call() throws Exception {
				GetVideoResponse response = mongoDBManager.getVideoListForUser(user);
				LOG.info("Recieved videoList from Services: " + response);
				return response;
			}
		};
	}

}

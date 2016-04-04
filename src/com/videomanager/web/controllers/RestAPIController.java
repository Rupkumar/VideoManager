package com.videomanager.web.controllers;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.videomanager.model.VideoList;

@RestController
public class RestAPIController {

	private static final Log LOG = LogFactory.getLog(RestAPIController.class);

	@RequestMapping("/api/videos/{user}")
	public Callable<VideoList> getUserVideos(@PathVariable String user) {
		return new Callable<VideoList>() {
			@Override
			public VideoList call() throws Exception {
				LOG.info("Recieved exposures from Services: ");
				return null;
			}
		};
	}

}

package com.videomanager.web.controllers;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.videomanager.model.GetUserVideoListResponse;
import com.videomanager.mongodb.dao.VideoManagerDAO;


@RestController
public class RestAPIController {
	
	@Autowired
	private VideoManagerDAO videoManagerDAO;

	private static final Log LOG = LogFactory.getLog(RestAPIController.class);

	@RequestMapping("/api/myvideos/{user}")
	public Callable<GetUserVideoListResponse> getUserVideoList(@PathVariable String user) {
		return new Callable<GetUserVideoListResponse>() {
			@Override
			public GetUserVideoListResponse call() throws Exception {
				GetUserVideoListResponse response = videoManagerDAO.getVideoListForUser(user, true);
				LOG.info("Recieved videoList from Services: " + response);
				return response;
			}
		};
	}
	
	@RequestMapping("/api/showvideos/{user}")
	public Callable<GetUserVideoListResponse> getUserVideos(@PathVariable String user) {
		return new Callable<GetUserVideoListResponse>() {
			@Override
			public GetUserVideoListResponse call() throws Exception {
				GetUserVideoListResponse response = videoManagerDAO.getVideoListForUser(user, true);
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
					String userName = "rupkumar";
					boolean privateFlag = false;
					videoManagerDAO.saveVideoForUser(userName, privateFlag, file);
					LOG.info("Saved the file " + file.getOriginalFilename());
				}
				return null;
			}
		};
	}
	
	@RequestMapping(value = "/api/video/{filename}" , method = RequestMethod.GET)
	//public void fetchvideo(HttpServletResponse response, HttpServletRequest request, @PathVariable String filename) {
	public ResponseEntity<byte[]> fetchvideo(@PathVariable String filename) {
	    LOG.info("Start  fetch video : " + filename);
	    GridFSDBFile file = null;
	    try{
	    	file = videoManagerDAO.getVideoFile(filename);
	        final HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        ByteArrayOutputStream output = new ByteArrayOutputStream();
    		file.writeTo(output);
	        return new ResponseEntity<byte[]>(output.toByteArray(), headers, HttpStatus.OK);
	    } catch(Exception e) {
	    	LOG.error("Error: "+e,e);

	    } finally {
	    	LOG.debug("End fetchvideo");
	    }
	    return null;
	}

}

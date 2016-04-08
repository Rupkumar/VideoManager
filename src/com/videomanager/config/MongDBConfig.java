package com.videomanager.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.videomanager.mongodb.dao.VideoManagerDAO;
import com.videomanager.mongodb.dao.impl.VideoManagerDAOImpl;

@Configuration
public class MongDBConfig {

	@Autowired
	private MongoClient mongoClient;
	
	@Bean
	public DB connectDB() throws UnknownHostException {
		//MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("videodb");
		return db;
	}
	
	@Bean
	public VideoManagerDAO videoManagerDAO() {
		VideoManagerDAOImpl manager = new VideoManagerDAOImpl();
		return manager;
	}
}

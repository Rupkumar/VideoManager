package com.videomanager.config;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.fasterxml.jackson.databind.ObjectMapper;
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

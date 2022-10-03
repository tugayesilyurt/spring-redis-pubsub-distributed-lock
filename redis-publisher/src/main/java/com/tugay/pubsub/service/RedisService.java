package com.tugay.pubsub.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tugay.pubsub.model.ProductRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

	private final RedisTemplate<String, String> redisTemplateString;
	private ObjectMapper mapper = new ObjectMapper();

	@Scheduled(fixedRate = 30000)
	public void publish() throws JsonProcessingException {
		
		ProductRequest product = ProductRequest.builder().productName("lock-product").build();
		String json = mapper.writeValueAsString(product);
		this.redisTemplateString.convertAndSend("update-stock", json);
		this.redisTemplateString.opsForValue().set("update-stock", json);
		log.info("Message Send : " + product.getProductName());
		
	}

}
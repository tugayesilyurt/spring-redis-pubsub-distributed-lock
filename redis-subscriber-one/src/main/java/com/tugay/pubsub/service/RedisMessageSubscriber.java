package com.tugay.pubsub.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {
	
	private final ProductService productService;

	@Override
	public void onMessage(final Message message, final byte[] pattern) {
		
		productService.updateStock(message);
		
	}


}
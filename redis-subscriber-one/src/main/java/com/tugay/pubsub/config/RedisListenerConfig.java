package com.tugay.pubsub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.tugay.pubsub.service.RedisMessageSubscriber;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class RedisListenerConfig {

	private final RedisMessageSubscriber redisMessageSubscriber;

	@Bean
	MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(redisMessageSubscriber);
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
		RedisMessageListenerContainer mlc = new RedisMessageListenerContainer();

		mlc.setConnectionFactory(redisConnectionFactory);
		mlc.addMessageListener(new MessageListenerAdapter(redisMessageSubscriber), new ChannelTopic("update-stock"));
		return mlc;
	}


}
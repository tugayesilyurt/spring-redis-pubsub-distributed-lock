package com.tugay.pubsub.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class RedissonClientConfig {

	@Bean
	public RedissonClient redissionClient() {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://127.0.0.1:6379").setTimeout(5000);
		return Redisson.create(config);

	}
}

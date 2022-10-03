package com.tugay.pubsub.service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.connection.Message;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tugay.pubsub.model.Product;
import com.tugay.pubsub.model.ProductRequest;
import com.tugay.pubsub.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

	private final RedissonClient redisson;
	private final ProductRepository productRepository;
	private ObjectMapper mapper = new ObjectMapper();

	@Retryable(value = org.redisson.client.RedisTimeoutException.class, maxAttempts = 2, backoff = @Backoff(delay = 2000))
	public void updateStock(final Message message) {

		RLock lock = redisson.getLock("update-stock-lock");

		try {

			lock.lock(4, TimeUnit.SECONDS);

			log.info("RedissonClient Lock By : Subscriber ONE");
			String body = new String(message.getBody(), StandardCharsets.UTF_8);

			ProductRequest requestProduct=null;
			try {
				requestProduct = mapper.readValue(body, ProductRequest.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			Optional<Product> product = productRepository.findByProductName(requestProduct.getProductName());
			if (product.isPresent()) {
				product.get().setStock(product.get().getStock() > 1 ? product.get().getStock() - 1 : 0);
				product.get().setLastUpdateBy("SUBSCRIBER ONE");
				productRepository.save(product.get());
			}

		}  finally {
			if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
				lock.unlock();
				log.info("lock released by ONE");
			}
		}
	}

}

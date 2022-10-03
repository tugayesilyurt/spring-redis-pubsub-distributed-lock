package com.tugay.pubsub.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tugay.pubsub.model.Product;
import com.tugay.pubsub.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InsertProduct implements CommandLineRunner{

	private final ProductRepository productRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		productRepository.deleteAll();
		productRepository.save(Product.builder().productName("lock-product").stock(20).lastUpdateBy(null).build());
		
	}
	
}

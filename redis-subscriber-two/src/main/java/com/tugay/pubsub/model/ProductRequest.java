package com.tugay.pubsub.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest implements Serializable {

	private static final long serialVersionUID = 5406729150891519476L;
	
	private String productName;
	private Integer stock;
	private String lastUpdateBy;

}

package com.cassini.foodzone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddRecipeRequest {
	
	private String recipeName;
	private Double unitPrice;
	private Integer vendorId;

}

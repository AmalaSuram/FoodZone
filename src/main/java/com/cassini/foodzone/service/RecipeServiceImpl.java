package com.cassini.foodzone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cassini.foodzone.dto.AddRecipeRequest;
import com.cassini.foodzone.dto.AddRecipeResponseDto;
import com.cassini.foodzone.dto.EditRecipeResponseDto;
import com.cassini.foodzone.entity.Recipe;
import com.cassini.foodzone.entity.Vendor;
import com.cassini.foodzone.exception.NotFoundException;
import com.cassini.foodzone.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	RecipeRepository recipeRepository;

	/**
	 * This method is used to get the list of Recipes
	 */

	@Override
	public List<Recipe> getAllRecipes(Integer vendorId) {

		log.info("starting getAllRecipes method , inside RecipeServiceImpl");
		Vendor vendor = new Vendor();
		vendor.setVendorId(vendorId);
		return recipeRepository.findByVendor(vendor);
	}

	@Override
	public AddRecipeResponseDto addRecipe(AddRecipeRequest addRecipeRequest) {
		Recipe recipe = new Recipe();
		BeanUtils.copyProperties(addRecipeRequest, recipe);
		Vendor vendor = new Vendor();
		vendor.setVendorId(addRecipeRequest.getVendorId());
		recipe.setVendor(vendor);
		recipeRepository.save(recipe);
		AddRecipeResponseDto addRecipeResponseDto = new AddRecipeResponseDto();
		addRecipeResponseDto.setRecipeId(recipe.getRecipeId());
		return addRecipeResponseDto;
	}

	@Override
	public EditRecipeResponseDto editRecipe(Integer recipeId) throws NotFoundException {
		Optional<Recipe> recipe = recipeRepository.findById(recipeId);
		if (recipe.isPresent()) {
			if (recipe.get().getStatus().equals("yes")) {
				recipe.get().setStatus("no");
			} else {
				recipe.get().setStatus("yes");
			}
			recipeRepository.save(recipe.get());
		} else {
			throw new NotFoundException("recipe not found");
		}
		return new EditRecipeResponseDto();
	}

}

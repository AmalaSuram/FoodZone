package com.cassini.foodzone.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.cassini.foodzone.dto.AddRecipeRequest;
import com.cassini.foodzone.entity.Recipe;
import com.cassini.foodzone.exception.NotFoundException;
import com.cassini.foodzone.repository.RecipeRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RecipeServiceTest {
	
	@InjectMocks
	RecipeServiceImpl recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Test
	public void testGetAllRecipesSuccess() {
		
		Mockito.when(recipeRepository.findByVendor(Mockito.any())).thenReturn(new ArrayList<>());
		assertNotNull(recipeService.getAllRecipes(1));
		
	}
	
	@Test
	public void testAddRecipeSuccess() {
		AddRecipeRequest addRecipeRequest = new AddRecipeRequest();
		addRecipeRequest.setRecipeName("test");
		addRecipeRequest.setUnitPrice(1.0);
		addRecipeRequest.setVendorId(1);
		Mockito.when(recipeRepository.save(Mockito.any())).thenReturn(new Recipe());
		assertNotNull(recipeService.addRecipe(addRecipeRequest));
	}
	
	@Test(expected = NotFoundException.class)
	public void testEditRecipeException() throws NotFoundException {
		Mockito.when(recipeRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
		recipeService.editRecipe(1);
		
	}
	
	@Test
	public void testEditRecipeYes() throws NotFoundException {
		Recipe recipe = new Recipe();
		recipe.setStatus("yes");
		Mockito.when(recipeRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(recipe));
		Mockito.when(recipeRepository.save(Mockito.any())).thenReturn(new Recipe());
		assertNotNull(recipeService.editRecipe(1));
		
	}
	
	@Test
	public void testEditRecipeNo() throws NotFoundException {
		Recipe recipe = new Recipe();
		recipe.setStatus("no");
		Mockito.when(recipeRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(recipe));
		Mockito.when(recipeRepository.save(Mockito.any())).thenReturn(new Recipe());
		assertNotNull(recipeService.editRecipe(1));
		
	}

}

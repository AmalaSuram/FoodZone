package com.cassini.foodzone.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.cassini.foodzone.dto.GetOrderRequestDto;
import com.cassini.foodzone.dto.OrderRequestDto;
import com.cassini.foodzone.entity.Customer;
import com.cassini.foodzone.entity.CustomerOrder;
import com.cassini.foodzone.entity.Recipe;
import com.cassini.foodzone.entity.Vendor;
import com.cassini.foodzone.exception.NotFoundException;
import com.cassini.foodzone.repository.CustomerOrderRepository;
import com.cassini.foodzone.repository.RecipeRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderServiceImplTest {
	
	@InjectMocks
	CustomerOrderServiceImpl customerOrderServiceImpl;
	
	@Mock
	CustomerOrderRepository customerOrderRepository;
	@Mock
	RecipeRepository recipeRepository;
	
	List<CustomerOrder> orders = new ArrayList<>();
	CustomerOrder customerOrder = new CustomerOrder();
	OrderRequestDto orderRequestDto = new OrderRequestDto();
	
	
	@Before
	public void setup() {
		
		customerOrder.setOrderId(1);
		orders.add(customerOrder);
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		orderRequestDto.setCustomerId(1);
		orderRequestDto.setRecipes(list);	
	}
	
	@Test
	public void testGetOrdersForCustomer() {
		Customer customer = new Customer();
		customer.setCustomerId(1);
		Mockito.when(customerOrderRepository.findByCustomer(Mockito.any())).thenReturn(orders);
		GetOrderRequestDto getOrderRequestDto = new GetOrderRequestDto();
		getOrderRequestDto.setCustomerId(1);
		Integer actual = customerOrderServiceImpl.getOrders(getOrderRequestDto).get(0).getOrderId();
		Integer expected = 1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetOrdersForVendor() {
		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		Mockito.when(customerOrderRepository.findByVendorAndOrderStatus(Mockito.any(), Mockito.any())).thenReturn(orders);
		GetOrderRequestDto getOrderRequestDto = new GetOrderRequestDto();
		getOrderRequestDto.setVendorId(1);
		getOrderRequestDto.setStatus("test");
		Integer actual = customerOrderServiceImpl.getOrders(getOrderRequestDto).get(0).getOrderId();
		Integer expected = 1;
		assertEquals(expected, actual);
	}
	
	@Test(expected = NotFoundException.class)
	public void testPlaceOrderError() throws NotFoundException {
		Recipe recipe = new Recipe();
		recipe.setRecipeId(1);
		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		recipe.setVendor(vendor);
		Mockito.when(recipeRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
		customerOrderServiceImpl.placeOrder(orderRequestDto);
		
	}
	
	@Test()
	public void testPlaceOrderSuccess() throws NotFoundException {
		Recipe recipe = new Recipe();
		recipe.setRecipeId(1);
		Vendor vendor = new Vendor();
		vendor.setVendorId(1);
		recipe.setVendor(vendor);
		Mockito.when(recipeRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(recipe));
		Mockito.when(customerOrderRepository.save(Mockito.any())).thenReturn(new CustomerOrder());
		assertNotNull(customerOrderServiceImpl.placeOrder(orderRequestDto)); 
		
		
	}



}

package com.cassini.foodzone.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import com.cassini.foodzone.dto.LoginRequestDto;
import com.cassini.foodzone.dto.LoginResponseDto;
import com.cassini.foodzone.dto.RegistrationDto;
import com.cassini.foodzone.dto.ResponseDto;
import com.cassini.foodzone.entity.Customer;
import com.cassini.foodzone.exception.NotFoundException;
import com.cassini.foodzone.exception.RegistrationFailedExcpetion;
import com.cassini.foodzone.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerServiceImplTest {

	@InjectMocks
	CustomerServiceImpl customerServiceImpl;
	@Mock
	CustomerRepository customerRepository;

	@Test
	public void testRegisterPossitive() throws RegistrationFailedExcpetion {

		RegistrationDto registrationDto = new RegistrationDto();
		registrationDto.setCustomerName("amala");
		registrationDto.setEmail("amala@gmail.com");
		registrationDto.setPassword("sweetysandy");
		registrationDto.setPhoneNumber(7680920258L);

		Customer customer = new Customer();
		BeanUtils.copyProperties(registrationDto, customer);

		Mockito.when(customerRepository.save(customer)).thenReturn(customer);

		ResponseDto userregistration = customerServiceImpl.registration(registrationDto);
		assertNotNull(userregistration);

	}

	@Test(expected = RegistrationFailedExcpetion.class)
	public void testRegisterException() throws RegistrationFailedExcpetion {

		RegistrationDto registrationDto = new RegistrationDto();
		registrationDto.setCustomerName(null);
		Customer customer = new Customer();
		BeanUtils.copyProperties(registrationDto, customer);
		customerServiceImpl.registration(registrationDto);

	}
	
	@Test
	public void testAuthenticateCustomer() throws NotFoundException {

		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmail("amala@gmail.com");
		loginRequestDto.setPassword("sweety");
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setEmail("amala@gmail.com");
		loginResponseDto.setId(1);
		loginResponseDto.setName("amala");
		loginResponseDto.setPhoneNumber(7680920258L);

		Customer customer = new Customer();
		BeanUtils.copyProperties(loginResponseDto, customer);

		Mockito.when(
				customerRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword()))
				.thenReturn(Optional.of(customer));

		LoginResponseDto authenticateCustomer = customerServiceImpl.authenticateCustomer(loginRequestDto);

		assertEquals(customer.getEmail(), "amala@gmail.com");

	}

	@Test(expected = NotFoundException.class)
	public void testAuthenticateCustomerExc() throws NotFoundException {

		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmail("amala@gmail.com");
		loginRequestDto.setPassword("sweety");
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setEmail("amala@gmail.com");
		loginResponseDto.setId(1);
		loginResponseDto.setName("amala");
		loginResponseDto.setPhoneNumber(7680920258L);
		Customer customer = new Customer();
		BeanUtils.copyProperties(loginResponseDto, customer);

		Mockito.when(customerRepository.findByEmailAndPassword(null, loginRequestDto.getPassword()))
				.thenReturn(Optional.of(customer));

		customerServiceImpl.authenticateCustomer(loginRequestDto);

	}

	
	
}

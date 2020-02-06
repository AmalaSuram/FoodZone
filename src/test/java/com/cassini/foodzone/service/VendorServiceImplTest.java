package com.cassini.foodzone.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
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
import com.cassini.foodzone.dto.RegisterVendorRequestDto;
import com.cassini.foodzone.dto.RegisterVendorResponseDto;
import com.cassini.foodzone.entity.Vendor;
import com.cassini.foodzone.exception.NotFoundException;
import com.cassini.foodzone.repository.VendorRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class VendorServiceImplTest {

	@InjectMocks
	VendorServiceImpl vendorServiceImpl;
	@Mock
	VendorRepository vendorRepository;

	@Test
	public void testGetAllVendors() {
		List<Vendor> listOfVendors = new ArrayList<Vendor>();
		Vendor vendor = new Vendor();
		vendor.setEmail("amala@gmail.com");
		vendor.setPassword("sweety");
		vendor.setPhoneNumber(76876567679L);
		vendor.setVendorId(1);
		vendor.setVendorName("amala");
		Vendor vendor2 = new Vendor();
		vendor.setEmail("pandu@gmail.com");
		vendor.setPassword("sweety");
		vendor.setPhoneNumber(76876567679L);
		vendor.setVendorId(2);
		vendor.setVendorName("amala");
		listOfVendors.add(vendor);

		listOfVendors.add(vendor2);

		Mockito.when(vendorRepository.findAll()).thenReturn(listOfVendors);

		List<Vendor> allVendors = vendorServiceImpl.getAllVendors();
		assertEquals(2, allVendors.size());

	}

	@Test
	public void testAuthenticateVendor() throws NotFoundException {

		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmail("ahsj@gmail.com");
		loginRequestDto.setPassword("xyz");

		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setEmail("ahsj@gmail.com");
		loginResponseDto.setId(1);
		loginResponseDto.setPhoneNumber(8765476789081L);
		loginResponseDto.setName("xyz");

		Vendor vendor = new Vendor();
		BeanUtils.copyProperties(loginResponseDto, vendor);

		Mockito.when(vendorRepository.findByEmailAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword()))
				.thenReturn(Optional.of(vendor));

		LoginResponseDto authenticateVendor = vendorServiceImpl.authenticateVendor(loginRequestDto);

		assertEquals(authenticateVendor.getEmail(), "ahsj@gmail.com");

	}

	@Test
	public void testRegisterVendor() {

		RegisterVendorRequestDto registerVendorRequestDto = new RegisterVendorRequestDto();
		registerVendorRequestDto.setEmail("ash@gmail.com");
		registerVendorRequestDto.setPhoneNumber(5465787908L);
		registerVendorRequestDto.setVendorName("ajgfjh");

		Vendor vendor = new Vendor();

		RegisterVendorResponseDto registerVendorResponseDto = new RegisterVendorResponseDto();

		BeanUtils.copyProperties(registerVendorResponseDto, vendor);
		Mockito.when(vendorRepository.save(vendor)).thenReturn(vendor);

		RegisterVendorResponseDto registerVendor = vendorServiceImpl.registerVendor(registerVendorRequestDto);
      assertNotEquals(0, registerVendor.getStatusCode());
	
	}

}

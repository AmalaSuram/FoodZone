package com.cassini.foodzone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cassini.foodzone.dto.RegistrationDto;
import com.cassini.foodzone.dto.ResponseDto;
import com.cassini.foodzone.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	ResponseDto save(RegistrationDto registrationDto);

}

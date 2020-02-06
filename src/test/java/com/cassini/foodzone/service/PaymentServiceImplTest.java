package com.cassini.foodzone.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.cassini.foodzone.entity.Payment;
import com.cassini.foodzone.repository.PaymentRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PaymentServiceImplTest {

	@InjectMocks
	PaymentServiceImpl paymentServiceImpl;
	@Mock
	PaymentRepository paymentRepository;

	@Test
	public void testGetPaymentMode() {

		List<Payment> listOfPayments = new ArrayList<Payment>();
		Payment payment = new Payment();
		payment.setPaymentId(1);
		payment.setPaymentName("googlepay");
		Payment payment1 = new Payment();
		payment.setPaymentId(1);
		payment.setPaymentName("phonepay");
		listOfPayments.add(payment);
		listOfPayments.add(payment1);

		Mockito.when(paymentRepository.findAll()).thenReturn((listOfPayments));

		List<Payment> paymentMode = paymentServiceImpl.getPaymentMode();
		assertEquals(2, paymentMode.size());

	}

}

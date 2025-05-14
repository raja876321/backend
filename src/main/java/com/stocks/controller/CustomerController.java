package com.stocks.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.dto.CustomerDTO;
import com.stocks.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

	private  final CustomerService customerService;

	// ✅ Get customer by phone number (customer side)
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDTO> getCustomerByPhoneAndShop(@RequestParam String phone,
			@RequestParam String shopId) {
		CustomerDTO customer = customerService.getCustomerByPhoneAndShop(phone, shopId);
		return ResponseEntity.ok(customer);
	}

	public ResponseEntity<CustomerDTO> getCustomerByPhone(@RequestParam String phone) {
		CustomerDTO customer = customerService.getCustomerByPhone(phone);
		return ResponseEntity.ok(customer);
	}

	// ✅ Get all customers by shop ID (shopkeeper side)
	@GetMapping("/shop/{shopId}")
	public ResponseEntity<Page<CustomerDTO>> getAllCustomersByShop(@PathVariable String shopId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		Page<CustomerDTO> customers = customerService.getAllCustomersByShop(shopId, page, size, sortBy);
		return ResponseEntity.ok(customers);
	}
}

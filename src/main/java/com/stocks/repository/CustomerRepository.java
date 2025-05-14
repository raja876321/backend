package com.stocks.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stocks.entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByPrimaryPhoneContainingAndShopId(String phone, String shopId);

	Page<Customer> findAllByShopId(String shopId, Pageable pageable);

	Optional<Customer> findByPrimaryPhone(String phone);
}

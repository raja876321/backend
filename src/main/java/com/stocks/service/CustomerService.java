package com.stocks.service;

import org.springframework.data.domain.Page;

import com.stocks.dto.CustomerDTO;

public interface CustomerService {
    CustomerDTO getCustomerByPhoneAndShop(String phone, String shopId);
    CustomerDTO getCustomerByPhone(String phone);
    Page<CustomerDTO> getAllCustomersByShop(String shopId, int page, int size, String sortBy);
}

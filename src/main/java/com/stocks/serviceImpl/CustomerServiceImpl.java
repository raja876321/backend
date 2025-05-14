package com.stocks.serviceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.stocks.dto.CustomerDTO;
import com.stocks.dto.CustomerMapper;
import com.stocks.entity.Customer;
import com.stocks.exception.ResourceNotFoundException;
import com.stocks.repository.CustomerRepository;
import com.stocks.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO getCustomerByPhoneAndShop(String phone, String shopId) {
        Customer customer = customerRepository.findByPrimaryPhoneContainingAndShopId(phone, shopId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return CustomerMapper.toDTO(customer);
    }

    @Override
    public Page<CustomerDTO> getAllCustomersByShop(String shopId, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return customerRepository.findAllByShopId(shopId, pageable).map(CustomerMapper::toDTO);
    }

	@Override
	public CustomerDTO getCustomerByPhone(String phone) {
		 Customer customer = customerRepository.findByPrimaryPhone(phone)
		            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		        return CustomerMapper.toDTO(customer);
	}
}

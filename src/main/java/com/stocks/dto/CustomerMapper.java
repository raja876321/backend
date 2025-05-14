package com.stocks.dto;

import com.stocks.entity.Customer;

//CustomerMapper.java
public class CustomerMapper {
	public static CustomerDTO toDTO(Customer customer) {
		CustomerDTO dto = new CustomerDTO();
		dto.setId(customer.getId());
		dto.setName(customer.getName());
		dto.setPhoneNumbers(customer.getAlternatePhones());
		dto.setProfilePhoto(customer.getProfilePhotoUrl());
		dto.setDefaultAddress(customer.getPrimaryAddress());
		dto.setAddresses(customer.getAddresses());

		if (customer.getDocuments() != null) {
			dto.setDocuments(customer.getDocuments().stream().map(doc -> {
				DocumentDTO d = new DocumentDTO();
				d.setId(doc.getId());
				d.setDocumentName(doc.getDocumentName());
				d.setFileType(doc.getDocumentName());
				d.setUploadedDate(doc.getUploadedDate());
				return d;
			}).toList());
		}

		return dto;
	}
}

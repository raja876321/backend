package com.stocks.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
	private Long id;
	private String name;
	private List<String> phoneNumbers;
	private String defaultAddress;
	private String profilePhoto;
	private List<String> addresses;
	private List<DocumentDTO> documents;
}
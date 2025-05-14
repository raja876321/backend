package com.stocks.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//Customer.java
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String primaryPhone;
	private String primaryAddress;
	private String shopId;
	private String profilePhotoUrl;

	@ElementCollection
	private List<String> alternatePhones;

	@ElementCollection
	private List<String> addresses;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<CustomerDocument> documents;

	@OneToMany(mappedBy = "customer")
	private List<Sale> sales;
}

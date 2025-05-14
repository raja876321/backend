package com.stocks.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dues {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "sale_id", nullable = true)
	private Sale sale;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private BigDecimal totalDue;
	private BigDecimal totalPaid;
	private BigDecimal remainingDue;

	@OneToMany(mappedBy = "dues", cascade = CascadeType.ALL)
	private List<PartialPayment> partialPayments;
}
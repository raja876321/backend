package com.stocks.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Sale.java
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private String shopId;
	private String shopName;
	private String invoiceNumber;
	private LocalDate saleDate;
	private BigDecimal totalAmount;
	private BigDecimal amountWithGst;
	private BigDecimal amountWithoutGst;
	private BigDecimal gstPercentage;

	private boolean isPaid;

	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode; // FULL, DUES, EMI

	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod; // CASH, UPI, BANK_CARD

	private BigDecimal downPayment;
	private BigDecimal extraCharges;
	private BigDecimal accessoriesCost;
	private BigDecimal repairCharges;
	private BigDecimal totalDiscount;
	private BigDecimal totalPayableAmount;

	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
	private List<SaleItem> items;

	@Lob
	private byte[] invoicePdf; // Invoice as encrypted binary
}
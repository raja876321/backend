package com.stocks.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stocks.constant.constant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseBill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long billId;
	
	private String shopId;
	private LocalDate date;
	private String companyName;

	private boolean isPaid;

	@Column(precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(precision = 10, scale = 2)
	private BigDecimal withoutGst;

	@Column(precision = 5, scale = 2)
	private BigDecimal gst;

	@Column(precision = 10, scale = 2)
	private BigDecimal dues;

	@OneToMany(mappedBy = "bill", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<BillMobileItem> items = new ArrayList<>();
	
	@PrePersist
    public void prePersist() {
        if (this.shopId == null) {
            this.shopId = constant.DEFAULT_SHOP_ID;
        }
    }
}

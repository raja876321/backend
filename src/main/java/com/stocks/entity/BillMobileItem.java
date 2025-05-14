package com.stocks.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stocks.constant.constant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class BillMobileItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long BillMobileItem;

	@ManyToOne()
	@JoinColumn(name = "billId")
	@JsonBackReference
	private PurchaseBill bill;

	private String shopId;
	private String model;
	private Double sellingPrice;
	private short ram;
	private short rom;
	private String color;
	private short qty;
	private String company;
	private String logo;
	
	@PrePersist
    public void prePersist() {
        if (this.shopId == null) {
            this.shopId = constant.DEFAULT_SHOP_ID;
        }
    }
}

package com.stocks.entity;


import com.stocks.constant.constant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mobile_phones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MobilePhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shopId=constant.DEFAULT_SHOP_ID;
    private String model;
    private String ram;
    private String rom;
    private String color;
    private Double sellingPrice;
    private Integer qty;
    private String company;
    private String logo;
    
    @PrePersist
    public void prePersist() {
        if (this.shopId == null) {
            this.shopId = constant.DEFAULT_SHOP_ID;
        }
    }	
}
 
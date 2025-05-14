package com.stocks.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleItemDTO {
    private String model;
    private String color;
    private String ram;
    private String rom;
    private int quantity;
    private BigDecimal sellingPrice;
    private String accessoryName;
    private boolean accessoryIncluded;
}

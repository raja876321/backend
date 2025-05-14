package com.stocks.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleDTO {
    private Long saleId;
    private String shopId;
    private String shopName;
    private String invoiceNumber;
    private LocalDate saleDate;
    private BigDecimal totalPayableAmount;
    private String customerName;
    private List<SaleItemDTO> items;
}

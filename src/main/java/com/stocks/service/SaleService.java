package com.stocks.service;

import java.util.List;

import org.springframework.data.domain.Page;


import com.stocks.dto.SaleDTO;
import com.stocks.entity.Sale;

//SaleService.java
public interface SaleService {
 Sale createSale(Sale sale);
 List<SaleDTO> getSalesByCustomer(Long customerId, String shopId);
 SaleDTO getSaleById(Long saleId);
 Page<SaleDTO> getSalesByShop(String shopId, int page, int size, String sortBy);
}

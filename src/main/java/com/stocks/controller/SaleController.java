package com.stocks.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.dto.SaleDTO;
import com.stocks.entity.Sale;
import com.stocks.service.SaleService;

import lombok.RequiredArgsConstructor;

//SaleController.java
@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

 private final SaleService saleService;

 @PostMapping("/create")
 public ResponseEntity<?> createSale(@RequestBody Sale sale) {
     try {
         return ResponseEntity.ok(saleService.createSale(sale));
     } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
     }
 }

 @GetMapping("/shop/{shopId}")
 public ResponseEntity<Page<SaleDTO>> getShopSales(@PathVariable String shopId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "id") String sortBy) {
     return ResponseEntity.ok(saleService.getSalesByShop(shopId, page, size, sortBy));
 }

 @GetMapping("/customer")
 public ResponseEntity<List<SaleDTO>> getCustomerSales(@RequestParam Long customerId, @RequestParam String shopId) {
     return ResponseEntity.ok(saleService.getSalesByCustomer(customerId, shopId));
 }

 @GetMapping("/{saleId}")
 public ResponseEntity<SaleDTO> getSaleById(@PathVariable Long saleId) {
     return ResponseEntity.ok(saleService.getSaleById(saleId));
 }
}

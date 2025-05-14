
package com.stocks.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.constant.constant;
import com.stocks.entity.PurchaseBill;
import com.stocks.serviceImpl.PurchseServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor

public class PurchaseBillController {

   
    private final PurchseServiceImpl service;

    @PostMapping("/add")
    public ResponseEntity<PurchaseBill> addBill(@RequestBody PurchaseBill bill) {
        PurchaseBill savedBill = service.savePurchaseBill(bill);
        return new ResponseEntity<>(savedBill, new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<PurchaseBill>> getAllBills(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort) {
        Page<PurchaseBill> bills = service.getAllPurchaseBills(constant.DEFAULT_SHOP_ID, page, size, sort);
        return new ResponseEntity<>(bills, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{billId}")
    public ResponseEntity<PurchaseBill> getBill(@PathVariable Long billId) {
        PurchaseBill bill = service.getPurchaseBill(constant.DEFAULT_SHOP_ID, billId);
        return new ResponseEntity<>(bill, new HttpHeaders(), bill != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{billId}")
    public ResponseEntity<String> deleteBill(@PathVariable Long billId) {
        String response = service.deletePurchaseBill(constant.DEFAULT_SHOP_ID, billId);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}



package com.stocks.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.stocks.entity.PurchaseBill;

@Service
public interface PurchaseBillService {
	
	PurchaseBill savePurchaseBill(PurchaseBill purcahsebill);
	Page<PurchaseBill> getAllPurchaseBills(String shopId,int page, int size, String sortBy);
	String deletePurchaseBill (String shopId,Long billId);
	PurchaseBill getPurchaseBill (String shopId,Long billId);
	
}

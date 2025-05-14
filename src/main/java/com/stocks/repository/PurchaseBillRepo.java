package com.stocks.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stocks.entity.PurchaseBill;


public interface PurchaseBillRepo extends JpaRepository<PurchaseBill, Long>{
	
	Page<PurchaseBill> findByShopId(String shopId,Pageable pagable);

    Optional<PurchaseBill> findByShopIdAndBillId(String shopId, Long billId);

    Long deleteByShopIdAndBillId(String shopId, Long billId);

}

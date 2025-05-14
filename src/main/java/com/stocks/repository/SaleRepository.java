package com.stocks.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stocks.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Page<Sale> findAllByShopId(String shopId, Pageable pageable);
    List<Sale> findByCustomerIdAndShopId(Long customerId, String shopId );
    Optional<Sale> findById(Long saleId);
}
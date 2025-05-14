package com.stocks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stocks.entity.MobilePhone;

public interface InventoryRepository extends JpaRepository<MobilePhone, Long> {

    Optional<MobilePhone> findByShopIdAndModelAndRamAndRomAndColorAndCompany(
            String shopId,
            String model,
            String ram,
            String rom,
            String color,
            String company);

    Page<MobilePhone> findByShopId(String shopId, Pageable pageable);

    List<MobilePhone> findByShopIdAndQtyLessThan(String shopId, Integer qty);

    List<MobilePhone> findByShopIdAndCompany(String shopId, String company);

    List<MobilePhone> findByShopId(String shopId); // used in summary & overview
}

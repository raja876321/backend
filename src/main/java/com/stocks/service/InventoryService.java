package com.stocks.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.stocks.dto.CompanyInventorySummary;
import com.stocks.dto.InventoryOverviewDTO;
import com.stocks.entity.MobilePhone;

public interface InventoryService {
	
	MobilePhone saveOrUpdatePhone(MobilePhone phone);  // update phone 

	Page<MobilePhone> getAllByShopId(String shopId, int page, int size, String sortBy);

	MobilePhone getByIdAndShopId(Long id, String shopId);

	List<MobilePhone> getLowStockItems(String shopId, int minQty);

	List<CompanyInventorySummary> getCompanySummary(String shopId, int lowStockThreshold);

	List<MobilePhone> filterInventory(String shopId, String company, String model, String color, String ram, String rom);

	InventoryOverviewDTO getInventoryOverview(String shopId, int lowStockThreshold);

	List<String> getModelsByCompany(String shopId, String company);
}

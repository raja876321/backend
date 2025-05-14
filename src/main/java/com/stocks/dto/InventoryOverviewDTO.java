package com.stocks.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryOverviewDTO {
    private long totalCompanies;
    private long totalModels;
    private long totalStockQuantity;
    private long totalLowStockItems;
    private List<String> allCompanies;
    private List<String> allModels;

    // constructor, getters, setters
    public InventoryOverviewDTO(long totalCompanies, long totalModels, long totalStockQuantity,
                                long totalLowStockItems, List<String> allCompanies, List<String> allModels) {
        this.totalCompanies = totalCompanies;
        this.totalModels = totalModels;
        this.totalStockQuantity = totalStockQuantity;
        this.totalLowStockItems = totalLowStockItems;
        this.allCompanies = allCompanies;
        this.allModels = allModels;
    }

    // Getters and setters omitted for brevity
}

package com.stocks.dto;

public class CompanyInventorySummary {
    private String company;
    private long totalStock;
    private long totalModels;
    private long lowStockModels;

    public CompanyInventorySummary(String company, long totalStock, long totalModels, long lowStockModels) {
        this.company = company;
        this.totalStock = totalStock;
        this.totalModels = totalModels;
        this.lowStockModels = lowStockModels;
    }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public long getTotalStock() { return totalStock; }
    public void setTotalStock(long totalStock) { this.totalStock = totalStock; }

    public long getTotalModels() { return totalModels; }
    public void setTotalModels(long totalModels) { this.totalModels = totalModels; }

    public long getLowStockModels() { return lowStockModels; }
    public void setLowStockModels(long lowStockModels) { this.lowStockModels = lowStockModels; }
}

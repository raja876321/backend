

package com.stocks.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.constant.constant;
import com.stocks.dto.CompanyInventorySummary;
import com.stocks.dto.InventoryOverviewDTO;
import com.stocks.entity.MobilePhone;
import com.stocks.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {

  
    private final  InventoryService inventoryService;

    @GetMapping("/shop")
    public ResponseEntity<Page<MobilePhone>> getInventoryByShopId(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort) {
        Page<MobilePhone> result = inventoryService.getAllByShopId(constant.DEFAULT_SHOP_ID, page, size, sort);
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MobilePhone> getInventoryItemByIdAndShop(@PathVariable Long id) {
        MobilePhone phone = inventoryService.getByIdAndShopId(id, constant.DEFAULT_SHOP_ID);
        return new ResponseEntity<>(phone, new HttpHeaders(), phone != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<MobilePhone>> getLowStock(@RequestParam int minQty) {
        List<MobilePhone> data = inventoryService.getLowStockItems(constant.DEFAULT_SHOP_ID, minQty);
        return new ResponseEntity<>(data, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<CompanyInventorySummary>> getSummary(
            @RequestParam(defaultValue = "5") int lowStockQty) {
        List<CompanyInventorySummary> summary = inventoryService.getCompanySummary(constant.DEFAULT_SHOP_ID, lowStockQty);
        return new ResponseEntity<>(summary, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MobilePhone>> filterInventory(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String ram,
            @RequestParam(required = false) String rom) {
        List<MobilePhone> result = inventoryService.filterInventory(
                constant.DEFAULT_SHOP_ID, company, model, color, ram, rom);
        return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/overview")
    public ResponseEntity<InventoryOverviewDTO> getOverview(@RequestParam(defaultValue = "5") int lowStockQty) {
        InventoryOverviewDTO overview = inventoryService.getInventoryOverview(constant.DEFAULT_SHOP_ID, lowStockQty);
        return new ResponseEntity<>(overview, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/company-models")
    public ResponseEntity<List<String>> getModelsByCompany(@RequestParam String company) {
        List<String> list = inventoryService.getModelsByCompany(constant.DEFAULT_SHOP_ID, company);
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }
}






package com.stocks.dto;

import java.util.List;

import com.stocks.entity.Sale;

//SaleMapper.java
public class SaleMapper {
	public static SaleDTO toDTO(Sale sale) {
		SaleDTO dto = new SaleDTO();
		dto.setSaleId(sale.getId());
		dto.setShopId(sale.getShopId());
		dto.setShopName(sale.getShopName());
		dto.setInvoiceNumber(sale.getInvoiceNumber());
		dto.setSaleDate(sale.getSaleDate());
		dto.setTotalPayableAmount(sale.getTotalPayableAmount());
		dto.setCustomerName(sale.getCustomer().getName());

		if (sale.getItems() != null) {
			dto.setItems(sale.getItems().stream().map(item -> {
				SaleItemDTO i = new SaleItemDTO();
				i.setModel(item.getModel());
				i.setColor(item.getColor());
				i.setRam(item.getRam());
				i.setRom(item.getRom());
				i.setQuantity(item.getQuantity());
				i.setSellingPrice(item.getSellingPrice());
				i.setAccessoryIncluded(item.isAccessoryIncluded());
				i.setAccessoryName(item.getAccessoryName());
				return i;
			}).toList());
		}

		return dto;
	}

	public static List<SaleDTO> toDTOList(List<Sale> sales) {
		return sales.stream().map(SaleMapper::toDTO).toList();
	}
}

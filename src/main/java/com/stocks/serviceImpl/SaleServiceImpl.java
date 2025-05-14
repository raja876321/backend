package com.stocks.serviceImpl;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.stocks.dto.SaleDTO;
import com.stocks.dto.SaleMapper;
import com.stocks.entity.Customer;
import com.stocks.entity.MobilePhone;
import com.stocks.entity.Sale;
import com.stocks.entity.SaleItem;
import com.stocks.exception.ResourceNotFoundException;
import com.stocks.repository.CustomerRepository;
import com.stocks.repository.InventoryRepository;
import com.stocks.repository.SaleRepository;
import com.stocks.service.SaleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final InventoryRepository inventoryRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Sale createSale(Sale sale) {
        if (sale == null || sale.getItems() == null || sale.getCustomer() == null) {
            throw new IllegalArgumentException("Sale object is incomplete");
        }

        Customer customer = customerRepository.findById(sale.getCustomer().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        for (SaleItem item : sale.getItems()) {
            item.setSale(sale);
            Optional<MobilePhone> opt = inventoryRepository.findByShopIdAndModelAndRamAndRomAndColorAndCompany(
                sale.getShopId(), item.getModel(), item.getRam(), item.getRom(), item.getColor(), item.getAccessoryName()
            );

            if (opt.isEmpty()) {
                throw new ResourceNotFoundException("Product not found in inventory: " + item.getModel());
            }

            MobilePhone stockItem = opt.get();
            if (stockItem.getQty() < item.getQuantity()) {
                throw new IllegalArgumentException("Insufficient quantity for model: " + item.getModel());
            }

            stockItem.setQty(stockItem.getQty() - item.getQuantity());
            inventoryRepository.save(stockItem);
        }

        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());
        return saleRepository.save(sale);
    }

    @Override
    public Page<SaleDTO> getSalesByShop(String shopId, int page, int size, String sortBy) {
        Pageable pageable =  PageRequest.of(page, size, Sort.by(sortBy).descending());
        return saleRepository.findAllByShopId(shopId, pageable).map(SaleMapper::toDTO);
    }

    @Override
    public List<SaleDTO> getSalesByCustomer(Long customerId, String shopId) {
        return SaleMapper.toDTOList(saleRepository.findByCustomerIdAndShopId(customerId, shopId));
    }

    @Override
    public SaleDTO getSaleById(Long saleId) {
        return saleRepository.findById(saleId)
            .map(SaleMapper::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
    }

	
}

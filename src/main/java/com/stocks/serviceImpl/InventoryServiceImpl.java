package com.stocks.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.stocks.dto.CompanyInventorySummary;
import com.stocks.dto.InventoryOverviewDTO;
import com.stocks.entity.MobilePhone;
import com.stocks.repository.InventoryRepository;
import com.stocks.service.InventoryService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    
    private final InventoryRepository inventoryRepo;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MobilePhone saveOrUpdatePhone(MobilePhone phone) {
        Optional<MobilePhone> existing = inventoryRepo.findByShopIdAndModelAndRamAndRomAndColorAndCompany(
                phone.getShopId(),
                phone.getModel(),
                phone.getRam(),
                phone.getRom(),
                phone.getColor(),
                phone.getCompany()
        );

        if (existing.isPresent()) {
            MobilePhone dbPhone = existing.get();
            dbPhone.setQty(dbPhone.getQty() + phone.getQty());
            inventoryRepo.save(dbPhone);
        } else {
            inventoryRepo.save(phone);
        }
		return phone;
    }

    @Override
    public Page<MobilePhone> getAllByShopId(String shopId, int page, int size, String sortBy) {
    	
    	Sort sort = Sort.by(sortBy).ascending();
    	Pageable pageable= PageRequest.of(page, size, sort);
        return inventoryRepo.findByShopId(shopId,pageable);
              
    }

    @Override
    public MobilePhone getByIdAndShopId(Long id, String shopId) {
        return inventoryRepo.findById(id)
                .filter(phone -> shopId.equals(phone.getShopId()))
                .orElse(null);
    }
    
    @Override
    public List<MobilePhone> getLowStockItems(String shopId, int minQty) {
        return inventoryRepo.findByShopIdAndQtyLessThan(shopId, minQty);
    }
    
    public List<CompanyInventorySummary> getCompanySummary(String shopId, int lowStockThreshold) {
        List<MobilePhone> allPhones = inventoryRepo.findByShopId(shopId);

        return allPhones.stream()
            .collect(Collectors.groupingBy(MobilePhone::getCompany))
            .entrySet().stream()
            .map(entry -> {
                String company = entry.getKey();
                List<MobilePhone> phones = entry.getValue();

                long totalStock = phones.stream().mapToInt(MobilePhone::getQty).sum();
                long modelCount = phones.size();
                long lowStockModels = phones.stream().filter(p -> p.getQty() < lowStockThreshold).count();

                return new CompanyInventorySummary(company, totalStock, modelCount, lowStockModels);
            })
            .toList();
    }

 
	@Override
    public List<MobilePhone> filterInventory(String shopId, String company, String model, String color, String ram, String rom) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MobilePhone> query = cb.createQuery(MobilePhone.class);
        Root<MobilePhone> root = query.from(MobilePhone.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("shopId"), shopId));

        if (company != null) predicates.add(cb.equal(cb.lower(root.get("company")), company.toLowerCase()));
        if (model != null) predicates.add(cb.equal(cb.lower(root.get("model")), model.toLowerCase()));
        if (color != null) predicates.add(cb.equal(cb.lower(root.get("color")), color.toLowerCase()));
        if (ram != null) predicates.add(cb.equal(root.get("ram"), ram));
        if (rom != null) predicates.add(cb.equal(root.get("rom"), rom));

        query.select(root).where(predicates.toArray(new Predicate[0]));
        return  entityManager.createQuery(query).getResultList();
    }

    @Override
    public InventoryOverviewDTO getInventoryOverview(String shopId, int lowStockThreshold) {
        List<MobilePhone> phones = inventoryRepo.findByShopId(shopId);

        Set<String> companies = new HashSet<>();
        Set<String> models = new HashSet<>();
        long totalStock = 0;
        long lowStock = 0;

        for (MobilePhone phone : phones) {
            companies.add(phone.getCompany());
            models.add(phone.getModel());
            totalStock += phone.getQty();
            if (phone.getQty() < lowStockThreshold) {
                lowStock++;
            }
        }

        return new InventoryOverviewDTO(
            companies.size(),
            models.size(),
            totalStock,
            lowStock,
            new ArrayList<>(companies),
            new ArrayList<>(models)
        );
    }

    @Override
    public List<String> getModelsByCompany(String shopId, String company) {
        return inventoryRepo.findByShopIdAndCompany(shopId, company.toLowerCase())
                            .stream()
                            .map(MobilePhone::getModel)
                            .distinct()
                            .toList();
    }


}

package com.stocks.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.stocks.constant.constant;
import com.stocks.entity.BillMobileItem;
import com.stocks.entity.MobilePhone;
import com.stocks.entity.PurchaseBill;
import com.stocks.repository.PurchaseBillRepo;
import com.stocks.service.InventoryService;
import com.stocks.service.PurchaseBillService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchseServiceImpl  implements PurchaseBillService{
	
	
	private  final PurchaseBillRepo billrepo;
	

	private final InventoryService inventoryService;

	@Override
	public PurchaseBill savePurchaseBill(PurchaseBill purchasebill) {
	    // Set default values
	    purchasebill.setPaid(false);
	    purchasebill.setDues(purchasebill.getAmount());
	    purchasebill.setDate(LocalDate.now());

	    List<BillMobileItem> items = purchasebill.getItems();

	    if (items == null || items.isEmpty()) {
	        throw new IllegalArgumentException("At least one item is required in the bill.");
	    }

	    List<MobilePhone> mobilePhonesToSave = new ArrayList<>();

	    for (BillMobileItem item : items) {
	        // Validations
	        if (item.getQty() <= 0 ||
	            item.getModel() == null || item.getColor() == null || item.getCompany() == null ||
	            item.getSellingPrice() == null || item.getRam() == 0 || item.getRom() == 0) {
	            throw new IllegalArgumentException("Invalid item: All fields except logo must be non-null and qty > 0.");
	        }

	        // Normalize to lowercase
	        item.setModel(item.getModel().toLowerCase());
	        item.setColor(item.getColor().toLowerCase());
	        item.setCompany(item.getCompany().toLowerCase());

	        if (item.getLogo() == null || item.getLogo().isBlank()) {
	            item.setLogo("photo added");
	        }
	        // 🔥 Add this line to establish the link
	        item.setBill(purchasebill);
	        
	        // Create MobilePhone object from item
	        MobilePhone phone = new MobilePhone();
	        phone.setShopId(constant.DEFAULT_SHOP_ID);
	        phone.setModel(item.getModel());
	        phone.setRam(String.valueOf(item.getRam()));
	        phone.setRom(String.valueOf(item.getRom()));
	        phone.setColor(item.getColor());
	        phone.setSellingPrice(item.getSellingPrice());
	        phone.setQty((int) item.getQty());
	        phone.setCompany(item.getCompany());
	        phone.setLogo(item.getLogo());

	        mobilePhonesToSave.add(phone);
	    }

	    // Save all phones (Assuming you have a MobilePhoneRepository)
	    for (MobilePhone phone : mobilePhonesToSave) {
	        inventoryService.saveOrUpdatePhone(phone);
	    }

	    // Save the bill
	    return billrepo.save(purchasebill);
	}


	@Override
	public Page<PurchaseBill> getAllPurchaseBills(String shopId,int page, int size, String sortBy) {
		
		Sort sort=Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size,sort) ;
	    return billrepo.findByShopId(shopId,pageable);
	}

	@Override
	public PurchaseBill getPurchaseBill(String shopId, Long billId) {
	    return billrepo.findByShopIdAndBillId(shopId, billId)
	            .orElseThrow(() -> new RuntimeException("No bill found for shopId: " + shopId + ", billId: " + billId));
	}

	@Override
	public String deletePurchaseBill(String shopId, Long billId) {
	    Optional<PurchaseBill> billOpt = billrepo.findByShopIdAndBillId(shopId, billId);
	    if (billOpt.isPresent()) {
	        billrepo.deleteById(billId);
	        return "Bill deleted successfully for billId: " + billId;
	    } else {
	        return "Bill not found for deletion";
	    }
	}


}

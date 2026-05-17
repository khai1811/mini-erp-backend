package org.example.manager.service;

import jakarta.transaction.Transactional;
import org.example.manager.dto.response.InventoryResponse;
import org.example.manager.dto.response.ProductResponse;
import org.example.manager.entity.Inventory;
import org.example.manager.entity.Product;
import org.example.manager.repository.InventoryRepository;
import org.example.manager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // tầng xử lí nghiệp vụ kho
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;

    // repository dùng để lưu lịch sử nhập/xuất kho
    @Autowired
    private InventoryRepository inventoryRepository;

    // =====================================================
    // 1. NHẬP KHO
    // =====================================================

    @Transactional
    public ProductResponse importStock(Long productId,
                                       Integer amount,
                                       String note) {

        // kiểm tra số lượng nhập
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Số lượng nhập phải > 0");
        }

        // tìm sản phẩm theo ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy sản phẩm"));

        // lưu số lượng cũ
        int beforeQuantity = product.getQuantity();

        // cộng thêm tồn kho
        product.setQuantity(beforeQuantity + amount);

        // lưu database
        Product savedProduct = productRepository.save(product);

        // lưu lịch sử nhập kho
        saveLog(
                savedProduct,
                "IMPORT",
                beforeQuantity,
                amount,
                savedProduct.getQuantity(),
                note
        );

        // trả dữ liệu về client
        return mapProductResponse(savedProduct);
    }

    // =====================================================
    // 2. XUẤT KHO
    // =====================================================

    @Transactional
    public ProductResponse exportStock(Long productId,
                                       Integer amount,
                                       String note) {

        // kiểm tra số lượng xuất
        if (amount == null || amount <= 0) {
            throw new RuntimeException("Số lượng xuất phải > 0");
        }

        // tìm sản phẩm
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy sản phẩm"));

        // lưu số lượng hiện tại
        int beforeQuantity = product.getQuantity();

        // kiểm tra đủ hàng không
        if (beforeQuantity < amount) {
            throw new RuntimeException("Không đủ hàng trong kho");
        }

        // trừ tồn kho
        product.setQuantity(beforeQuantity - amount);

        // lưu database
        Product savedProduct = productRepository.save(product);

        // lưu lịch sử xuất kho
        saveLog(
                savedProduct,
                "EXPORT",
                beforeQuantity,
                amount,
                savedProduct.getQuantity(),
                note
        );

        // trả dữ liệu về client
        return mapProductResponse(savedProduct);
    }

    // =====================================================
    // 3. LẤY LỊCH SỬ KHO
    // =====================================================

    public List<InventoryResponse> getHistory(Long productId){

        return inventoryRepository.findByProductId(productId)
                .stream()
                .map(this::mapInventoryResponse)
                .toList();
    }

    // =====================================================
    // 4. HÀM LƯU LOG KHO
    // =====================================================

    private void saveLog(Product product,
                         String type,
                         Integer before,
                         Integer amount,
                         Integer after,
                         String note) {

        Inventory inventory = new Inventory();

        // gắn sản phẩm
        inventory.setProduct(product);

        // IMPORT / EXPORT
        inventory.setType(type);

        // số lượng trước thay đổi
        inventory.setBeforeQuantity(before);

        // số lượng thay đổi
        inventory.setAmount(amount);

        // số lượng sau thay đổi
        inventory.setAfterQuantity(after);

        // ghi chú
        inventory.setNote(note);

        // lưu database
        inventoryRepository.save(inventory);
    }

    // =====================================================
    // 5. PRODUCT -> PRODUCT RESPONSE
    // =====================================================

    private ProductResponse mapProductResponse(Product product){

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setSku(product.getSku());
        response.setQuantity(product.getQuantity());
        response.setExportPrice(product.getExportPrice());
        response.setCategory(product.getCategory());

        return response;
    }

    // =====================================================
    // 6. INVENTORY -> INVENTORY RESPONSE
    // =====================================================

    private InventoryResponse mapInventoryResponse(Inventory inventory){

        InventoryResponse response = new InventoryResponse();

        response.setId(inventory.getId());

        // lấy tên sản phẩm
        response.setProductName(
                inventory.getProduct().getName()
        );

        // IMPORT / EXPORT
        response.setType(
                inventory.getType()
        );

        // số lượng trước thay đổi
        response.setBeforeQuantity(
                inventory.getBeforeQuantity()
        );

        // số lượng thao tác
        response.setAmount(
                inventory.getAmount()
        );

        // số lượng sau thay đổi
        response.setAfterQuantity(
                inventory.getAfterQuantity()
        );

        // ghi chú
        response.setNote(
                inventory.getNote()
        );

        // thời gian tạo
        response.setCreatedAt(
                inventory.getCreateAt()
        );

        return response;
    }
}
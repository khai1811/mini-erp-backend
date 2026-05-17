// bước 5:
package org.example.manager.controller;

import org.example.manager.dto.response.InventoryResponse;
import org.example.manager.dto.response.ProductResponse;
import org.example.manager.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // =====================================================
    // 1. NHẬP KHO
    // POST: /api/inventory/import
    // =====================================================

    @PostMapping("/import")
    public ProductResponse importStock(
            @RequestParam Long productId,
            @RequestParam Integer amount,
            @RequestParam(required = false) String note
    ) {

        return inventoryService.importStock(
                productId,
                amount,
                note
        );
    }

    // =====================================================
    // 2. XUẤT KHO
    // =====================================================

    @PostMapping("/export")
    public ProductResponse exportStock(
            @RequestParam Long productId,
            @RequestParam Integer amount,
            @RequestParam(required = false) String note
    ) {

        return inventoryService.exportStock(
                productId,
                amount,
                note
        );
    }

    // =====================================================
    // 3. XEM LỊCH SỬ KHO
    // =====================================================

    @GetMapping("/history")
    public List<InventoryResponse> getHistory(@RequestParam Long productId) {
        return inventoryService.getHistory(productId);
    }
}
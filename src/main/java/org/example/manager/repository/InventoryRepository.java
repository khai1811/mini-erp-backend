// bước 2:


package org.example.manager.repository;

import org.example.manager.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    // tìm toàn bộ lịch sử kho theo product id
    List<Inventory> findByProductId(Long productId);}

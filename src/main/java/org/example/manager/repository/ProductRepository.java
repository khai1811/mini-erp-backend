// bước 2:
package org.example.manager.repository;

import org.example.manager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Tìm kiếm theo mã SKU để kiểm tra khi nhập hàng mới
    Optional<Product> findBySku(String sku);

    // tìm kiếm theo tên
    List<Product> findByNameContainingIgnoreCase(String name);
}
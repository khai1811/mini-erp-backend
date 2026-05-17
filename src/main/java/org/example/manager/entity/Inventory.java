package org.example.manager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "inventory_logs")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // sản phẩm liên quan
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // IMPORT / EXPORT / TRANSFER
    private String type;

    // số lượng trước khi thay đổi
    private Integer beforeQuantity;

    // số lượng thay đổi (+10 / -5)
    private Integer amount;

    // số lượng sau khi thay đổi
    private Integer afterQuantity;

    // ghi chú
    private String note;
// thời gian tự tạo log
    private LocalDateTime createAt;

    // tự gán thời gian hiện tại trước khi lưu vào data
@PrePersist
    protected void onCreate(){
    this.createAt = LocalDateTime.now();
}
}
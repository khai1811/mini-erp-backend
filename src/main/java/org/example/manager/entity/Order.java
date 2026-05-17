package org.example.manager.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mã đơn hàng
    @Column(unique = true)
    private String orderCode;

    // =========================
    // KHÁCH HÀNG
    // =========================
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // =========================
    // TRẠNG THÁI
    // =========================
    private String status;

    // CASH / MOMO / VNPAY
    private String paymentMethod;

    // Tổng tiền
    private Double totalAmount;

    // Ngày tạo đơn
    private LocalDateTime createdAt;

    // =========================
    // CHI TIẾT ĐƠN HÀNG
    // =========================
    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL)
    @JsonManagedReference   // Cho phép serialize phía cha
    private List<OrderItem> items;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
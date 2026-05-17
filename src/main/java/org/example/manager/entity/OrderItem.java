// Bước 1: bảng chi tiết đơn hàng

package org.example.manager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference   // Bỏ qua phía con để tránh vòng lặp
    private Order order; // thuộc đơn hàng nào
@ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // thuộc sản phẩm nào

    private Integer quantity ; // số lượng mua
    /**
     * GIÁ TẠI THỜI ĐIỂM MUA:
     * Cực kỳ quan trọng! Sau này nếu Product thay đổi giá,
     * hóa đơn cũ vẫn phải giữ nguyên giá lúc khách đã mua.
     */
    private Double priceAtPurchase;
}

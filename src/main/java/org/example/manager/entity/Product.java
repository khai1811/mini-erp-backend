// bước 1:

package org.example.manager.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity // thực thể đại diện cho bảng DB
@Data // tự tạo get/set nhờ thư viện lombock
@Table(name = "products")   // bảng db product
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id tăng tự động
    private Long id;

    private String name; // tên sản phẩm
    @Column(unique = true)
    private String sku; // mã định danh sản phẩm
    private Integer quantity; // số lượng sản phẩm
    private Double importPrice; // giá mua vào
    private Double exportPrice; // giá bán ra
    private String category ; // danh mục sản phẩm ( điện tử , đồ gia dụng)
    private LocalDateTime updateAt;
    @PrePersist  // chạy trước dũ liệu được lưu vào database , nó tự gán thời gian hiện tại
    @PreUpdate // chạy trước dữ liệu được update . sữa thông tin user trước khi update hàm này chạy

    public void updateTimestamp(){
        this.updateAt = LocalDateTime.now();
    }
}

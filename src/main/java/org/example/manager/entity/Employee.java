// bước 1 :tạo thực thể là mô tả của bảng db trong code java


package org.example.manager.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity // thực thể87cv
@Data // tự tạo set/get ( nhờ thư viện lombok)
@Table(name = "employees")
public class Employee {
    @Id // khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // id tăng tự động

    private Long id;
    private String fullName;
    @Column(unique = true) // tránh trùng email
    private String email;
    private String password;
    private String role;  // Admin ,  Staff
    private String position; // chức vụ
    private Double salary; // lương
    private  String phone;
    private  String status;
    private LocalDateTime createdAt;
    @PrePersist // chạy trc dữ liệu đc lưu vào database , nó tự động gán thời gian hiện tại
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}

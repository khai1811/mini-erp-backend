// bước 1: đầu tiên chúng ta cần 3 thực thể mới
// customer , order và orderitem


package org.example.manager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // chứa lombock tự động get/set
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tự động tăng id
    private Long id;
    private String name;
    private String phone;
    private String email;

    // điểm thưởng tích lũy
    private  Integer points = 0;
    // xếp hạng (normal , silver , gold, vip)
    private String rank = "NORMAL";
}

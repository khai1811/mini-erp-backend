
// bước 6: 🔥 2. Thư mục dto (Giấu giá nhập gốc đi)
//Tạo 2 thư mục con là request (nhận dữ liệu) và response (trả dữ liệu).

package org.example.manager.dto.response;

import lombok.Data;
@Data
public class ProductResponse {
   private Long id;
   private String name;
   private String sku;
   private Integer quantity;
   private Double exportPrice; // chỉ trả về giá bán giấu importPrice
    private String category;
}

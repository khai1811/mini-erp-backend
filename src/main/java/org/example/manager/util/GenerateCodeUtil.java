//bước 7 : 🔥 3. Thư mục util (Công cụ dùng chung)
//Thay vì bắt người dùng phải tự nghĩ ra SKU,
// ta tạo một công cụ tự động phát sinh
// mã SKU nếu họ bỏ trống.


package org.example.manager.util;

import java.util.UUID;

public class GenerateCodeUtil {
    // hàm static để dùng mọi noi không cần khởi tạo
    public static String generateSku(){
        // tạo mã ngẫu nhiên : Sp-abcd123
    return "Sp-"+ UUID.randomUUID().toString().substring(0,6).toUpperCase();

    }
}

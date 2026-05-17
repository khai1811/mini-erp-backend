
// bước 5: 🔥 1. Thư mục exception
// (Xử lý lỗi toàn cục)
//Chúng ta sẽ tạo một "lưới lọc"
// để bắt toàn bộ các RuntimeException mà
// bạn ném ra trong Service,
// và biến nó thành một cục JSON gọn gàng.

package org.example.manager.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
// @RestControllerAdvice:
// Dùng để bắt lỗi toàn cục cho tất cả Controller trong project
// Thay vì bị lỗi đỏ dài dòng ở server,
// ta sẽ trả về JSON đẹp và dễ hiểu cho client/Postman
public class GlobalExceptionHandler {

    // =========================================================
    // BẮT LỖI RuntimeException
    // =========================================================
    @ExceptionHandler(RuntimeException.class)

    // @ExceptionHandler:
    // Dùng để chỉ định loại lỗi muốn bắt
    // RuntimeException.class nghĩa là:
    // tất cả lỗi RuntimeException sẽ chạy vào đây

    public ResponseEntity<Map<String, String>>
    handleRuntimeException(RuntimeException ex) {

        // Tạo object dạng JSON để trả về
        Map<String, String> errorResponse = new HashMap<>();

        // ex.getMessage()
        // lấy nội dung lỗi
        // Ví dụ:
        // throw new RuntimeException("SKU đã tồn tại");
        // => sẽ lấy ra "SKU đã tồn tại"

        errorResponse.put("error", ex.getMessage());

        // custom trạng thái lỗi
        errorResponse.put("status", "BAD_REQUEST");

        // =====================================================
        // ResponseEntity
        // =====================================================

        // ResponseEntity:
        // dùng để trả HTTP response

        // badRequest()
        // tương ứng mã lỗi HTTP 400

        // .body(errorResponse)
        // trả dữ liệu JSON về client

        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }
}


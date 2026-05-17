package org.example.manager.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long customerId;
    private String paymentMethod;
    private List<CartItem>items; // danh sách sản phẩm trong giỏ hàng

    @Data
    public static class CartItem{
        private  Long productId;
        private Integer quantity;
    }
}

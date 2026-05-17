// bước 4 :
package org.example.manager.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryResponse {

    private Long id;

    private String productName;

    private String type;

    private Integer beforeQuantity;

    private Integer amount;

    private Integer afterQuantity;

    private String note;

    private LocalDateTime createdAt;
}
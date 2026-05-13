package com.mentorship.food_delivery_app.order.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private UUID menuItemId;
    private String itemName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal subtotal;
    private String note;
}
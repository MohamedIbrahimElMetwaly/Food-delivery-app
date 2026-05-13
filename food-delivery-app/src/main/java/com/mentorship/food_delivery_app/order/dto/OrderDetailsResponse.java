package com.mentorship.food_delivery_app.order.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsResponse {
    private UUID orderId;
    private String status;
    private String restaurantName;
    private BigDecimal orderSubtotal;
    private BigDecimal orderFee;
    private BigDecimal orderDiscountValue;
    private BigDecimal orderTotal;
    private Instant orderDate;
    private String orderNote;
    private List<OrderItemDto> items;
}
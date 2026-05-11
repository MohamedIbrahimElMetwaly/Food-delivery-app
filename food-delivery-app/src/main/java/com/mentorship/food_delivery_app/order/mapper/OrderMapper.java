package com.mentorship.food_delivery_app.order.mapper;

import com.mentorship.food_delivery_app.order.dto.CheckoutResponse;
import com.mentorship.food_delivery_app.order.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Maps {@link Orders} entities to checkout-time DTOs.
 *
 * <p>Same-named fields ({@code orderFee}, {@code orderDiscountValue},
 * {@code orderTotal}, {@code orderNote}) are auto-mapped by MapStruct, so they
 * are intentionally omitted below. {@code unmappedTargetPolicy = ERROR} makes
 * the build fail if a new {@link CheckoutResponse} field is added without a
 * matching source — preventing silently-null fields at runtime.
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OrderMapper {

    @Mapping(target = "orderId",        source = "id")
    @Mapping(target = "restaurantName", source = "restaurantBranch.restaurant.name")
    CheckoutResponse toCreateOrderResponse(Orders order);
}

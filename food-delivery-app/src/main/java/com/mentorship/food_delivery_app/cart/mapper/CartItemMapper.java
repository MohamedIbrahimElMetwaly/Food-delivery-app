package com.mentorship.food_delivery_app.cart.mapper;

import com.mentorship.food_delivery_app.cart.dto.AddToCartResponse;
import com.mentorship.food_delivery_app.cart.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CartItemMapper {
    AddToCartResponse toAddToCartResponse(CartItem cartItem);
}

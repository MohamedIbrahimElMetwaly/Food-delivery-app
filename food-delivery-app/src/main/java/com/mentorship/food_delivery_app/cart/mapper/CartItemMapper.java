package com.mentorship.food_delivery_app.cart.mapper;

import com.mentorship.food_delivery_app.cart.dto.AddToCartResponse;
import com.mentorship.food_delivery_app.cart.dto.UpdateQuantityResponse;
import com.mentorship.food_delivery_app.cart.entity.Cart;
import com.mentorship.food_delivery_app.cart.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface CartItemMapper {
    AddToCartResponse toAddToCartResponse(CartItem cartItem);
    @Mapping(source = "id", target = "cartItemId")
    UpdateQuantityResponse toUpdateQuantityResponse(CartItem cartItem);

}

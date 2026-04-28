package com.mentorship.food_delivery_app.cart.mapper;

import com.mentorship.food_delivery_app.cart.dto.CreateCartResponse;
import com.mentorship.food_delivery_app.cart.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface CartMapper {
    @Mapping(target = "cartId", source = "id")
    @Mapping(target = "customerId", source = "customer.id")
    CreateCartResponse toCreateCartResponse(Cart cart);
}

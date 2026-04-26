package com.mentorship.food_delivery_app.cart.mapper;

import com.mentorship.food_delivery_app.cart.dto.CreateCartRequest;
import com.mentorship.food_delivery_app.cart.dto.CreateCartResponse;
import com.mentorship.food_delivery_app.cart.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CartMapper {
    CreateCartResponse toCreateCartResponse(Cart cart);
}

package com.mentorship.food_delivery_app.cart.service;

import com.mentorship.food_delivery_app.cart.dto.*;
import com.mentorship.food_delivery_app.cart.entity.Cart;

public interface ICartService {


    CreateCartResponse createCart(CreateCartRequest cartRequest);

    AddToCartResponse addItemToCart(AddCartItemRequest request);


}


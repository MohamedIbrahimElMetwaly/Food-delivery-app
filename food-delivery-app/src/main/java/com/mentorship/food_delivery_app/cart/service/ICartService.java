package com.mentorship.food_delivery_app.cart.service;

import com.mentorship.food_delivery_app.cart.dto.*;

public interface ICartService {


    CreateCartResponse createCart(CreateCartRequest cartRequest);

    AddToCartResponse addItemToCart(AddCartItemRequest request);

    void clearCart(ClearCartRequest cartRequest);

    void  deleteCartItem(DeleteCartItemRequest deleteCartItemRequest);

    UpdateQuantityResponse updateQuantity(UpdateQuantityRequest updateQuantityRequest);


}


package com.mentorship.food_delivery_app.cart.service.impl;

import com.mentorship.food_delivery_app.cart.dto.*;
import com.mentorship.food_delivery_app.cart.entity.Cart;
import com.mentorship.food_delivery_app.cart.mapper.CartMapper;
import com.mentorship.food_delivery_app.cart.repository.CartItemRepository;
import com.mentorship.food_delivery_app.cart.repository.CartRepository;
import com.mentorship.food_delivery_app.cart.service.ICartService;
import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.customer.service.ICustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ICustomerService customerService;
    private final CartMapper cartMapper;


    @Transactional
    @Override
    public CreateCartResponse createCart(CreateCartRequest cartRequest) {
        Customer theCustomer = customerService.getCustomerWithCart(cartRequest.customerId());
        Cart cart = theCustomer.getCart();
        if (cart == null) {
           cart = Cart
                   .builder()
                   .customer(theCustomer)
                   .currentRestaurantBranchId(cartRequest.restaurantId())
                   .build();
           cartRepository.save(cart);

        }
        else {
            if( !cart.getCurrentRestaurantBranchId().equals(cartRequest.restaurantId())) {
                cart.getItems().clear();
            }
            cart.setCurrentRestaurantBranchId(cartRequest.restaurantId());
        }

        return cartMapper.toCreateCartResponse(cart);
    }

   
}

package com.mentorship.food_delivery_app.cart.service.impl;

import com.mentorship.food_delivery_app.cart.dto.AddCartItemRequest;
import com.mentorship.food_delivery_app.cart.dto.AddToCartResponse;
import com.mentorship.food_delivery_app.cart.dto.CreateCartRequest;
import com.mentorship.food_delivery_app.cart.dto.CreateCartResponse;
import com.mentorship.food_delivery_app.cart.entity.Cart;
import com.mentorship.food_delivery_app.cart.entity.CartItem;
import com.mentorship.food_delivery_app.cart.mapper.CartItemMapper;
import com.mentorship.food_delivery_app.cart.mapper.CartMapper;
import com.mentorship.food_delivery_app.cart.repository.CartRepository;
import com.mentorship.food_delivery_app.cart.service.ICartService;
import com.mentorship.food_delivery_app.common.exceptions.ResourceNotFoundException;
import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.customer.service.ICustomerService;
import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import com.mentorship.food_delivery_app.restaurant.repository.MenuItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final ICustomerService customerService;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final MenuItemRepository menuItemRepository;


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


    @Transactional
    @Override
    public AddToCartResponse addItemToCart(AddCartItemRequest request)  {
        MenuItem menuItem = menuItemRepository.findById(request.item().menuItemId()).orElseThrow(()->new ResourceNotFoundException(String.format("Menu Item with id : %s not found",request.item().menuItemId()), HttpStatus.NOT_FOUND.toString()));
        Customer theCustomer = customerService.getCustomerWithCart(request.customerId());
       Cart cart = theCustomer.getCart();
       if(cart == null) {
           cart = createNewCart(theCustomer);
       }

        cart.addItem(menuItem, request.item().quantity());

        cartRepository.save(cart);

    CartItem theCurrentCartItem = cart.getItems().stream().filter((cartItem)->cartItem.getMenuItem().getId().equals(menuItem.getId()))
                                                          .findFirst().orElseThrow();

        return cartItemMapper.toAddToCartResponse(theCurrentCartItem);
    }
private Cart createNewCart(Customer customer){
        return Cart.builder()
            .customer(customer)
            .build();

}

}

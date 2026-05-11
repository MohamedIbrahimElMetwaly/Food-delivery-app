package com.mentorship.food_delivery_app.cart.service.impl;

import com.mentorship.food_delivery_app.cart.dto.*;
import com.mentorship.food_delivery_app.cart.entity.Cart;
import com.mentorship.food_delivery_app.cart.entity.CartItem;
import com.mentorship.food_delivery_app.cart.exceptions.CartOwnershipMismatchException;
import com.mentorship.food_delivery_app.cart.mapper.CartItemMapper;
import com.mentorship.food_delivery_app.cart.mapper.CartMapper;
import com.mentorship.food_delivery_app.cart.repository.CartItemRepository;
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

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final ICustomerService customerService;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final MenuItemRepository menuItemRepository;
    private final CartItemRepository cartItemRepository;


    @Transactional
    @Override
    public CreateCartResponse createCart(CreateCartRequest cartRequest) {
        Customer theCustomer = customerService.getCustomerWithCart(cartRequest.customerId());
        Cart cart = theCustomer.getCart();
        if (cart == null) {
            cart = createNewCart(theCustomer);
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

        cart.addItem(menuItem, request.item().quantity(), request.item().note());

        cartRepository.save(cart);

    CartItem theCurrentCartItem = cart.getItems().stream().filter((cartItem)->cartItem.getMenuItem().getId().equals(menuItem.getId()))
                                                          .findFirst().orElseThrow();

        return cartItemMapper.toAddToCartResponse(theCurrentCartItem);
    }

    @Transactional
    @Override
    public void clearCart(ClearCartRequest cartRequest) {
        Customer theCustomer = customerService.getCustomerWithCart(cartRequest.customerId());
        Cart cart = theCustomer.getCart();
        if(cart ==null ){
            throw new ResourceNotFoundException("the cart is already empty",HttpStatus.NOT_FOUND.toString());
        }
        if(cart.getId().equals(cartRequest.cartId())) {
            throw new CartOwnershipMismatchException("The cart doesn't have the same id as the requested cart.");
        }
        cartItemRepository.deleteAllByCartId(cart.getId());
        cart.setCurrentRestaurantBranch(null);
        cart.setLocked(false);
    }

    @Transactional
    @Override
    public void deleteCartItem(DeleteCartItemRequest deleteCartItemRequest) {
       Cart cart =  validateAndGetCustomerCart(deleteCartItemRequest.customerId(), deleteCartItemRequest.cartId());

       CartItem currentCartItem = validateAndGetCartItem(deleteCartItemRequest.cartItemId(), cart);

       cart.removeItem(currentCartItem);
       cartRepository.save(cart);

    }

    @Transactional
    @Override
    public UpdateQuantityResponse updateQuantity(UpdateQuantityRequest updateQuantityRequest) {

        Customer theCustomer = customerService.getCustomerWithCart(updateQuantityRequest.customerId());
        Cart cart = theCustomer.getCart();

        CartItem currentCartItem= validateAndGetCartItem(updateQuantityRequest.cartItemId(), cart);

        currentCartItem.setQuantity(updateQuantityRequest.quantity());
        cartRepository.save(cart);
        return cartItemMapper.toUpdateQuantityResponse(currentCartItem);

    }

    private Cart createNewCart(Customer customer){
        return Cart.builder()
            .customer(customer)
            .build();
    }

    private Cart validateAndGetCustomerCart(UUID customerId,UUID cartId){

        Customer theCustomer = customerService.getCustomerWithCart(customerId);
        Cart cart = theCustomer.getCart();
        if(cart ==null ){
            throw new ResourceNotFoundException("the cart is already empty",HttpStatus.NOT_FOUND.toString());
        }
        if(!cart.getId().equals(cartId)) {
            throw new CartOwnershipMismatchException("The cart doesn't have the same id as the requested cart.");
        }
        return cart;
    }
    private CartItem validateAndGetCartItem(Long cartItemId,Cart cart){
       return cart.getItems().stream()
                .filter((cartItem)->cartItem.getId().equals(cartItemId))
                .findFirst().orElseThrow(
                        ()-> new ResourceNotFoundException(String.format("Cart Item with id : %s not found",cartItemId)
                                , HttpStatus.NOT_FOUND.toString())
                );
    }

}

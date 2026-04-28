package com.mentorship.food_delivery_app.cart.controller;

import com.mentorship.food_delivery_app.cart.dto.*;
import com.mentorship.food_delivery_app.cart.service.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final ICartService cartService;



    @PostMapping("/create")
    public ResponseEntity<CreateCartResponse> creatCart(@Valid @RequestBody CreateCartRequest request){

            return new ResponseEntity<>(cartService.createCart(request), HttpStatus.CREATED);

        }

    @PostMapping("/cart/items")
    public ResponseEntity<AddToCartResponse> addItemToCart(@Valid @RequestBody AddCartItemRequest request)  {
            return new ResponseEntity<>(cartService.addItemToCart(request),HttpStatus.OK);
        }

        @DeleteMapping
        public ResponseEntity<String> clearCart(@Valid @RequestBody ClearCartRequest request)  {
            cartService.clearCart(request);
            return ResponseEntity.noContent().build();

        }

    @DeleteMapping("/cart/items")
    public ResponseEntity<String> deleteCartItem(@Valid @RequestBody DeleteCartItemRequest request)  {
        cartService.deleteCartItem(request);
        return ResponseEntity.noContent().build();

    }
    @PatchMapping("/items")
    public ResponseEntity<UpdateQuantityResponse> updateQuantityCartItem(UpdateQuantityRequest request) {
        UpdateQuantityResponse response = cartService.updateQuantity(request);
        return ResponseEntity.ok().body(response);
    }

    }

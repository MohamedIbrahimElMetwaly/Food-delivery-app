package com.mentorship.food_delivery_app.cart.controller;

import com.mentorship.food_delivery_app.cart.dto.AddCartItemRequest;
import com.mentorship.food_delivery_app.cart.dto.AddToCartResponse;
import com.mentorship.food_delivery_app.cart.dto.CreateCartRequest;
import com.mentorship.food_delivery_app.cart.dto.CreateCartResponse;
import com.mentorship.food_delivery_app.cart.service.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    }

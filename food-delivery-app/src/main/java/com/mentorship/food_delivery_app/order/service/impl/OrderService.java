package com.mentorship.food_delivery_app.order.service.impl;

import com.mentorship.food_delivery_app.cart.entity.Cart;
import com.mentorship.food_delivery_app.cart.entity.CartItem;
import com.mentorship.food_delivery_app.common.exceptions.ResourceNotFoundException;
import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.customer.service.ICustomerService;
import com.mentorship.food_delivery_app.order.dto.CheckoutRequest;
import com.mentorship.food_delivery_app.order.dto.CheckoutResponse;
import com.mentorship.food_delivery_app.order.entity.OrderItem;
import com.mentorship.food_delivery_app.order.entity.OrderStatus;
import com.mentorship.food_delivery_app.order.entity.OrderTracking;
import com.mentorship.food_delivery_app.order.entity.Orders;
import com.mentorship.food_delivery_app.order.exceptions.EmptyCartException;
import com.mentorship.food_delivery_app.order.exceptions.InvalidCartException;
import com.mentorship.food_delivery_app.order.mapper.OrderMapper;
import com.mentorship.food_delivery_app.order.repository.IOrderRepository;
import com.mentorship.food_delivery_app.order.repository.OrderStatusRepository;
import com.mentorship.food_delivery_app.order.repository.OrderTrackingRepository;
import com.mentorship.food_delivery_app.order.service.IOrderService;
import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import com.mentorship.food_delivery_app.restaurant.service.IMenuItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    /** Service fee rate. TODO: pull from system_config.service_fee row. */
    private static final BigDecimal SERVICE_FEE_RATE = new BigDecimal("0.05");

    private final ICustomerService customerService;
    private final IMenuItemService menuItemService;
    private final IOrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderTrackingRepository orderTrackingRepository;


    @Transactional
    @Override
    public CheckoutResponse placeOrder(CheckoutRequest checkoutRequest) {
        // TODO Phase-2: derive customerId from checkoutRequest.cartId() or auth context.
        UUID customerId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        Customer customer = customerService.getCustomerWithCart(customerId);
        Cart cart = customer.getCart();

        if (cart == null) {
            throw new ResourceNotFoundException(
                    "There is not Cart associated with user",
                    HttpStatus.NOT_FOUND.toString());
        }

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            throw new EmptyCartException("Can't create an order for an empty cart");
        }

        List<UUID> menuItemsId = cartItems.stream()
                .map(cartItem -> cartItem.getMenuItem().getId())
                .toList();
        List<MenuItem> menuItems = menuItemService.getAllMenuItemsIds(menuItemsId);

        if (menuItems.size() != menuItemsId.size()) {
            List<UUID> foundItems = menuItems.stream().map(MenuItem::getId).toList();
            List<UUID> missedItems = menuItemsId.stream()
                    .filter(id -> !foundItems.contains(id)).toList();
            throw new InvalidCartException("Menu items no longer available" + missedItems);
        }

        OrderStatus pending = orderStatusRepository.findByName("PENDING")
                .orElseThrow(() -> new IllegalStateException(
                        "Seed missing: order_status row 'PENDING' not found"));

        Orders order = createNewOrder(customer);
        order.setOrderStatus(pending);
        order.setRestaurantBranch(cart.getCurrentRestaurantBranch());

        // Build line items + accumulate subtotal in a single pass.
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal unitPrice = cartItem.getMenuItem().getPrice();
            int qty = cartItem.getQuantity();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty));

            OrderItem orderItem = OrderItem.builder()
                    .menuItem(cartItem.getMenuItem())
                    .unitPrice(unitPrice)        // snapshot price at checkout time
                    .quantity(qty)
                    .subtotal(lineTotal)
                    .note(cartItem.getNote())
                    .build();

            order.addItem(orderItem);            // maintains both sides of the link
            subtotal = subtotal.add(lineTotal);
        }

        BigDecimal fee = subtotal.multiply(SERVICE_FEE_RATE);
        order.setOrderSubtotal(subtotal);
        order.setOrderFee(fee);
        order.setOrderDiscountValue(BigDecimal.ZERO);   // coupons not applied yet
        order.setOrderTotal(subtotal.add(fee));
        order.setOrderNote(checkoutRequest.orderNote());
        order.setCustomerAddress(customer.getDefaultAddress());

        orderRepository.save(order);                    // cascades to OrderItems

        // Initial history row marking the order as PENDING.
        OrderTracking tracking = OrderTracking.builder()
                .order(order)
                .orderStatus(pending)
                .orderTrackingDescription("Order placed")
                .build();
        orderTrackingRepository.save(tracking);

        // Clear the cart now that its contents have been moved to the order.
        // orphanRemoval = true on Cart.items will delete the rows on commit.
        cart.getItems().clear();

        return orderMapper.toCreateOrderResponse(order);
    }

    private Orders createNewOrder(Customer customer) {
        return Orders.builder()
                .customer(customer)
                .customerAddress(customer.getDefaultAddress())
                .build();
    }
}

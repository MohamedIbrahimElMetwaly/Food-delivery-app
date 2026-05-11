package com.mentorship.food_delivery_app.restaurant.service;

import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface IMenuItemService {
    List<MenuItem> getAllMenuItemsIds(List<UUID> ids);
}

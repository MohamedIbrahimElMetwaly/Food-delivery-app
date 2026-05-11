package com.mentorship.food_delivery_app.restaurant.service.impl;

import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import com.mentorship.food_delivery_app.restaurant.repository.MenuItemRepository;
import com.mentorship.food_delivery_app.restaurant.service.IMenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements IMenuItemService {
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItem> getAllMenuItemsIds(List<UUID> ids) {
        return menuItemRepository.findAllByIds(ids);
    }
}

package com.mentorship.food_delivery_app.restaurant.repository;

import com.mentorship.food_delivery_app.restaurant.entity.MenuItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import java.util.Optional;
import java.util.UUID;
public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    @EntityGraph(attributePaths = {"menu", "menu.restaurantBranch"})
    @NonNull
    Optional<MenuItem> findById(@NonNull UUID id);

}

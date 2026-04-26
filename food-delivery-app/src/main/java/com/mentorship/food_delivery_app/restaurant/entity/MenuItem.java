package com.mentorship.food_delivery_app.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "menu_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_item_id")
    private UUID id;

    @Column(name = "menu_item_name")
    private String name;

    @Column(name = "menu_item_description")
    private String description;

    @Column(name = "menu_item_price")
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "modified_by")
    private UUID modifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_menu_id", nullable = false)
    private RestaurantMenu menu;


}

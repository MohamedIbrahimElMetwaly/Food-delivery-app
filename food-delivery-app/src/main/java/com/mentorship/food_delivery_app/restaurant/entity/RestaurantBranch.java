package com.mentorship.food_delivery_app.restaurant.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "restaurant_branch")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "branch_id", updatable = false, nullable = false)
    private UUID id;


    @Column(name = "branch_delivery_fee", precision = 6, scale = 2)
    private BigDecimal deliveryFee;

    @Column(name = "branch_min_order", precision = 6, scale = 2)
    private BigDecimal minOrder;

    @Column(name = "branch_city", length = 20, nullable = false)
    private String city;

    @Column(name = "branch_open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "branch_close_time", nullable = false)
    private LocalTime closeTime;

    @Column(name = "branch_phone_number", length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "branch_estimated_delivery_time")
    private Integer estimatedDeliveryTime;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "modified_by")
    private UUID modifiedBy;

    @Column(name = "admin_id")
    private UUID adminId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_rest_id", nullable = false)
    private Restaurant restaurant;


    @OneToMany(mappedBy = "restaurantBranch",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<RestaurantMenu> menus;



}
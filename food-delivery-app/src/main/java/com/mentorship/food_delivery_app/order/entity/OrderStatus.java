package com.mentorship.food_delivery_app.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lookup row from the {@code order_status} table. Rows are seeded once via SQL
 * (PENDING, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED) and
 * the application only reads them — never inserts new ones at runtime.
 *
 * <p>The convention for referencing a status from the service layer is to look
 * it up by {@code name} (e.g. {@code "PENDING"}) rather than by id, so the code
 * never depends on the IDENTITY values produced by the seed insert.
 */
@Entity
@Table(name = "order_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "order_status_name", length = 20)
    private String name;

    @Column(name = "order_status_description", length = 255, nullable = false)
    private String description;
}

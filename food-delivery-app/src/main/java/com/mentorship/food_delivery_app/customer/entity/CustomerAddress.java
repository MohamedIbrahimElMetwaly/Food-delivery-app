package com.mentorship.food_delivery_app.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;

@Entity
@Table(name = "customer_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_address_id", updatable = false, nullable = false)
    private UUID customerAddressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_address_customer_id")
    private Customer customer;

    @Column(name = "customer_address_label")
    private String label;

    @Column(name = "customer_address_city")
    private String city;

    @Column(name = "customer_address_street")
    private String street;

    @Column(name = "customer_address_building")
    private String building;

    @Column(name = "customer_address_apartment")
    private String apartment;

    @Column(name = "customer_address_phone_number")
    private String phoneNumber;

    @Column(name = "customer_address_note")
    private String note;

}
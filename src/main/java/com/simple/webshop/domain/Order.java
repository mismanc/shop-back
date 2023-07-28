package com.simple.webshop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_gen")
    @SequenceGenerator(name = "order_id_gen", sequenceName = "order_id_seq")
    private Long id;

    @Column(nullable = false, scale = 2, precision = 10)
    private BigDecimal totalProductValue;

    @Column(nullable = false, scale = 2, precision = 10)
    private BigDecimal totalShippingValue;

    @Column(nullable = false, length = 200)
    private String clientName;

    @Column(nullable = false, length = 5000)
    private String clientAddress;

}

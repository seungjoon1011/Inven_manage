package com.example.Inven_manage.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name ="stocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Many OrderItems can belong to One Product
    @JoinColumn(name = "product_id", nullable = false) // Maps to the product_id column, enforcing NOT NULL at DB level
    @NotNull // Enforces NOT NULL at application level (Bean Validation)
    // ON DELETE CASCADE
    @OnDelete(action = OnDeleteAction.CASCADE) // Hibernate-specific: Generates ON DELETE CASCADE in DDL
    private Product product; // Reference to the Product entity

    @ManyToOne // Many OrderItems can belong to One Product
    @JoinColumn(name = "warehouse_id", nullable = false) // Maps to the product_id column, enforcing NOT NULL at DB level
    @NotNull // Enforces NOT NULL at application level (Bean Validation)
    // ON DELETE CASCADE
    @OnDelete(action = OnDeleteAction.CASCADE) // Hibernate-specific: Generates ON DELETE CASCADE in DDL
    private Warehouse warehouse; // Reference to the Product entity

    @Column
    @NotNull
    private int quantity;
}
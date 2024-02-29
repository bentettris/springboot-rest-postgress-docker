package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id")
    private Integer productId;

    private Integer quantity;

    @Column(name = "order_id")
    private Integer orderId;

    private Date created;

    private Date modified;

    public void update(OrderItem oi) {
        if(oi.getQuantity() != null) this.setQuantity(oi.getQuantity());
        if(oi.getModified() != null) this.setModified(oi.getModified());
    }
}

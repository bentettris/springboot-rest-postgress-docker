package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shipping_address_line_1")
    private String shippingAddressLine1;

    @Column(name = "shipping_address_line_2")
    private String shippingAddressLine2;

    @Column(name = "shipping_city")
    private String shippingCity;

    @Column(name = "shipping_region")
    private String shippingRegion;

    @Column(name = "shipping_postal_code")
    private String shippingPostalCode;

    @Column(name = "shipping_country")
    private String shippingCountry;

    private Date created;

    private Date modified;

    public void update(Order o) {
        if(o.shippingCity != null) this.setShippingCity(o.shippingCity);
        if(o.shippingCountry != null) this.setShippingCountry(o.shippingCountry);
        if(o.shippingRegion != null) this.setShippingRegion(o.shippingRegion);
        if(o.shippingPostalCode != null) this.setShippingPostalCode(o.shippingPostalCode);
        if(o.shippingAddressLine1 != null) this.setShippingAddressLine1(o.shippingAddressLine1);
        if(o.shippingAddressLine2 != null) this.setShippingAddressLine2(o.shippingAddressLine2);
        if(o.modified != null) this.setModified(o.getModified());
    }
}

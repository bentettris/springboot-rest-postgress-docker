package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String sku;

    private String name;

    private String description;

    private String price;

    private Date created;

    private Date modified;

    public void update(Product p) {
        if(p.getSku() != null) this.setSku(p.getSku());
        if(p.getName() != null) this.setName(p.getName());
        if(p.getDescription() != null) this.setDescription(p.getDescription());
        if(p.getPrice() != null) this.setPrice(p.getPrice());
        if(p.getModified() != null) this.setModified(p.getModified());
    }
}

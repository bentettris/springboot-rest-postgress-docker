package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @ResponseBody
    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id", required = false) Optional<Integer> id) {
        Product product = null;
        ResponseEntity<Product> response = null;
        try {
            product = productService.retrieveById(id.orElseThrow());
            if(product == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(product);
        }
        catch (NoSuchElementException e) {
            response = ResponseEntity.internalServerError().build();
            return response;
        }
    }


    @GetMapping(value = {"/"})
    public ResponseEntity<List<Product>> getAllProducts() {
        ResponseEntity<List<Product>> response = null;
        List<Product> products = new ArrayList<>();
        try {
            products = productService.getAll();
            if(products == null) throw new Exception("Null returned from product service getAll");
        }
        catch(Exception e) {
            response = ResponseEntity.internalServerError().build();
            return response;
        }
        if(products.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(products);
    }

    @PostMapping(value = {"/"})
    public ResponseEntity<Product> createProduct(@RequestBody Product p) {
        if(p == null) return ResponseEntity.badRequest().build();
        try {
            p.setCreated(new Date());
            p.setModified(new Date());
            Product returned = productService.create(p);
            if(returned == null) throw new Exception("Product creation has failed.");
            return ResponseEntity.ok(returned);
        }
        catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = {"/{id}"})
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product p) {
        ResponseEntity<Product> response = null;
        try {
            p.setModified(new Date());
            p.setId(id);
            Product pToUpdate = productService.retrieveById(id);
            if(pToUpdate == null) {
                return ResponseEntity.notFound().build();
            }
            else {
                pToUpdate.update(p);
                Product pUpdated = productService.update(pToUpdate);
                if(pUpdated == null) throw new Exception("Service update to Product returned a null.");
                return ResponseEntity.ok(pUpdated);
            }
        }
        catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity deleteProduct(@PathVariable Integer id) {
        try {
            boolean status = productService.deleteById(id);
            if(status == false) throw new Exception("Product Service delete call failed.");
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return ResponseEntity.internalServerError().build();
        }
    }

}

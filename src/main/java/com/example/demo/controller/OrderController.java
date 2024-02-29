package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ProductService productService;

    @ResponseBody
    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id", required = false) Optional<Integer> id) {
        Order o = null;
        try {
            o = orderService.getOrderById(id.orElseThrow());
            if(o == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(o);
        }
        catch(Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = {"/"})
    public ResponseEntity<List<Order>> getAllOrders() {
        ResponseEntity<List<Order>> response = null;
        List<Order> orders = null;
        try {
            orders = orderService.getAll();
            if(orders == null) throw new Exception("Null returned from order service getAll.");
        }
        catch(Exception e) {
            response = ResponseEntity.internalServerError().build();
            return response;
        }
        if(orders.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orders);
    }

    @PostMapping(value = {"/"})
    public ResponseEntity<Order> createOrder(@RequestBody Order o) {
        ResponseEntity<Product> response = null;
        if(o == null) return ResponseEntity.badRequest().build();
        try {
            o.setCreated(new Date());
            o.setModified(new Date());
            Order returned = orderService.createOrder(o);
            if(returned == null) throw new Exception("Order creation failed...it returned a null value instead of the newly created order.");
            return ResponseEntity.ok(returned);
        }
        catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = {"/{id}"})
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id,@RequestBody Order o) {
        ResponseEntity<Order> response = null;
        try {
            o.setModified(new Date());
            o.setId(id);
            Order oToUpdate = orderService.getOrderById(id);
            if(oToUpdate == null) {
                return ResponseEntity.notFound().build();
            }
            else {
                oToUpdate.update(o);
                Order updatedOrder = orderService.updateOrder(oToUpdate);
                if(updatedOrder == null) throw new Exception("Order service returned null attempting to update the order");
                return ResponseEntity.ok(updatedOrder);
            }
        }
        catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity deleteOrder(@PathVariable Integer id) {
        try {
            boolean status = orderService.deleteById(id);
            if(status == false) throw new Exception("Order service delete order returned false.");
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return ResponseEntity.internalServerError().build();
        }
    }
}

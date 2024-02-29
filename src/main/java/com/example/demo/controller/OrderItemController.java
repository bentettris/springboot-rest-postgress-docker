package com.example.demo.controller;

import com.example.demo.model.OrderItem;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/api/orderitem")
public class OrderItemController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ProductService productService;

    @ResponseBody
    @GetMapping(value = {"/{id}"})
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable(value = "id", required = false) Optional<Integer> id) {
        OrderItem oi = null;
        try {
            oi = orderItemService.findById(id.orElseThrow());
            if(oi == null) throw new Exception("Order item not returned for supplied id.");
            return ResponseEntity.ok(oi);
        }
        catch(NoSuchElementException e) {
            log.error(ExceptionUtils.getMessage(e));
            return ResponseEntity.internalServerError().build();
        }
        catch(Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = {"/"})
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        ResponseEntity<List<OrderItem>> response = null;
        List<OrderItem> orderItems = new ArrayList<>();
        try {
            orderItems = orderItemService.getAll();
            if(orderItems == null) {
                throw new Exception("Order item service getAll returned a null list.");
            }
        }
        catch(Exception e) {
            response = ResponseEntity.internalServerError().build();
            return response;
        }
        if(orderItems.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderItemService.getAll());
    }

    @PostMapping(value = {"/"})
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem oi) {
        if(oi == null) return ResponseEntity.badRequest().build();
        try {
            oi.setCreated(new Date());
            oi.setModified(new Date());
            OrderItem returned = orderItemService.create(oi);
            if(returned == null) throw new Exception("Order item service returned null during creation attempt.");
            return ResponseEntity.ok(returned);
        }
        catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = {"/{id}"})
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Integer id, @RequestBody OrderItem oi) {
        ResponseEntity<OrderItem> response = null;
        try {
            oi.setModified(new Date());
            oi.setId(id);
            OrderItem oiToUpdate = orderItemService.findById(id);
            if(oiToUpdate == null) {
                return ResponseEntity.notFound().build();
            }
            else {
                // Only updatable field is quantity
                oiToUpdate.update(oi);
                OrderItem updated = orderItemService.update(oiToUpdate);
                if(updated == null)
                    throw new Exception("Order item service returned a null when asked to pefrom an update");
                return ResponseEntity.ok(oiToUpdate);
            }
        }
        catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity deleteOrderItem(@PathVariable Integer id) {
        try {
            boolean status = orderItemService.deleteById(id);
            if(status == false) throw new Exception("Order Item service delete returned false.");
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return ResponseEntity.internalServerError().build();
        }
    }

}

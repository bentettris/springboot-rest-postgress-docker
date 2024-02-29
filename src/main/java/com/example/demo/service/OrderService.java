package com.example.demo.service;

import com.example.demo.db.OrderRepository;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    OrderItemService orderItemService;

    public Order createOrder(Order o) {
        return orderRepo.save(o);
    }

    public Order updateOrder(Order o) {
        return orderRepo.save(o);
    }

    public boolean deleteById(Integer id) {
        Optional<Order> orderOptional = orderRepo.findById(id);
        Order o = null;
        if(orderOptional.isPresent()) o = orderOptional.get();
        else return false;
        // Delete all the order items first
        List<OrderItem> orderItemList = orderItemService.findByOrderId(o.getId());
        orderItemService.deleteAll(orderItemList);
        orderRepo.delete(o);
        return true;
    }

    public Order getOrderById(Integer id) throws NoSuchElementException {
        Optional<Order> orderOptional = orderRepo.findById(id);
        if(orderOptional.isEmpty()) throw new NoSuchElementException();
        return orderOptional.get();
    }

    public List<OrderItem> addItemToOrder(Product p, Order o) {
        if(p == null || o == null) return new ArrayList<>();
        OrderItem oi = new OrderItem();
        oi.setProductId(p.getId());
        oi.setOrderId(o.getId());
        OrderItem newo = orderItemService.create(oi);
        return orderItemService.findByOrderId(o.getId());
    }

    public List<OrderItem> removeItemFromOrder(Order o, Product p) {
        if(o == null || p == null) {
            return new ArrayList<>();
        }
        OrderItem oi = orderItemService.findByOrderIdAndProductId(o.getId(), p.getId());
        if(oi != null) {
            orderItemService.deleteById(oi.getId());
        }
        return orderItemService.findByOrderId(o.getId());
    }

    public List<Order> getAll(){
        Iterable iterator = orderRepo.findAll();
        List<Order> listToReturn = new ArrayList<>();
        try{
            listToReturn = (List<Order>) StreamSupport.stream(iterator.spliterator(), false)
                    .collect(Collectors.toList());
            return listToReturn;
        }
        catch(Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            return listToReturn;
        }

    }
}

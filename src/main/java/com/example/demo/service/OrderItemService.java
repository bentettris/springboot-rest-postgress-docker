package com.example.demo.service;

import com.example.demo.db.OrderItemRepository;
import com.example.demo.model.OrderItem;
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
public class OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepo;

    public OrderItem create(OrderItem o) {
        return orderItemRepo.save(o);
    }

    public OrderItem update(OrderItem o) {
        return orderItemRepo.save(o);
    }

    public boolean deleteById(Integer id) {
        Optional<OrderItem> orderItemOptional =  orderItemRepo.findById(id);
        OrderItem oi = null;
        if(orderItemOptional.isPresent()) oi = orderItemOptional.get();
        if(oi == null) return false;
        orderItemRepo.delete(oi);
        return true;
    }

    public Boolean deleteAll(List<OrderItem> listToDelete) {
        if((listToDelete == null) || listToDelete.isEmpty()) return false;
        orderItemRepo.deleteAll(listToDelete);
        return true;
    }

    public OrderItem findById(Integer id) throws NoSuchElementException {
        Optional<OrderItem> orderOptional = orderItemRepo.findById(id);
        if(orderOptional.isPresent()) return orderOptional.get();
        else throw new NoSuchElementException();
    }

    public List<OrderItem> findByProductId(Integer id) {
        return orderItemRepo.findByProductId(id);
    }

    public List<OrderItem> findByOrderId(Integer id) {
        return orderItemRepo.findByOrderId(id);
    }

    public OrderItem findByOrderIdAndProductId(Integer orderId, Integer productId) {
        return orderItemRepo.findByOrderIdAndProductId(orderId,productId);
    }

    public List<OrderItem> getAll() {
        Iterable iterator = orderItemRepo.findAll();
        List<OrderItem> listToReturn = new ArrayList<>();
        try{
            listToReturn = (List<OrderItem>) StreamSupport.stream(iterator.spliterator(), false)
                    .collect(Collectors.toList());
            return listToReturn;
        }
        catch(Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            return listToReturn;
        }
    }


}

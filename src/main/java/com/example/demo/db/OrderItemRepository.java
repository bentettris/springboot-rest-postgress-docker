package com.example.demo.db;

import com.example.demo.model.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
    public List<OrderItem> findByProductId(Integer id);
    public List<OrderItem> findByOrderId(Integer id);

    public OrderItem findByOrderIdAndProductId(Integer orderId, Integer productId);
}

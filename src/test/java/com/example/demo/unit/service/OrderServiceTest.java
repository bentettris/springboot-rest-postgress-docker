package com.example.demo.unit.service;

import com.example.demo.db.OrderRepository;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderItemService orderItemService;


    @Test
    public void testCreate() {
        Order o = new Order();
        o.setCreated(new Date());
        o.setModified(new Date());
        o.setShippingPostalCode("USA");
        o.setShippingRegion("NY");
        o.setShippingCity("Brooklyn");
        o.setShippingPostalCode("11205");
        o.setShippingAddressLine1("100 Marcy Ave");
        o.setShippingAddressLine2("Apt 304");
        when(orderRepository.save(o)).thenReturn(o);
        Order oi = orderService.createOrder(o);
        assertThat(o).isEqualTo(o);
    }

    @Test
    public void testUpdate() {
        Order o = new Order();
        Order o2 = new Order();
        when(orderRepository.save(o)).thenReturn(o2);
        Order oReturned = orderService.updateOrder(o);
        assertThat(oReturned).isEqualTo(o2);
    }

    @Test
    public void testDeleteReturnTrue() {
        Order o = new Order();
        o.setId(1);
        when(orderRepository.findById(any(Integer.class))).thenReturn(Optional.of(o));
        doNothing().when(orderRepository).delete(o);
        Boolean ans = orderService.deleteById(o.getId());
        assertThat(ans).isEqualTo(true);
    }

    @Test
    public void testDeleteReturnFalse() {
        Order o = new Order();
        Boolean ans = orderService.deleteById(1);
        assertThat(ans).isEqualTo(false);
    }

    @Test
    public void findByIdSuccess() {
        Order oi = new Order();
        when(orderRepository.findById(any(Integer.class))).thenReturn(Optional.of(oi));
        Order oReturned = orderService.getOrderById(1);
        assertThat(oReturned).isEqualTo(oi);
    }

    @Test
    public void findByIdFailure() {
        assertThatThrownBy( () -> orderService.getOrderById(null)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testAddItemToOrderSuccess() {
        Order o = new Order();
        o.setId(1);
        Product p = new Product();
        p.setId(1);
        OrderItem oi = new OrderItem();
        oi.setProductId(p.getId());
        oi.setOrderId(p.getId());
        List<OrderItem> list = new ArrayList<>();
        list.add(oi);

        when(orderItemService.create(any(OrderItem.class))).thenReturn(oi);
        when(orderItemService.findByOrderId(o.getId())).thenReturn(list);
        List<OrderItem> orderItems = orderService.addItemToOrder(p,o);
        assertThat(list.contains(oi));
        assertThat(orderItems.contains(oi));
    }

    @Test
    public void testAddItemToOrderFailed() {
        List<OrderItem> orderItems = orderService.addItemToOrder(null,null);
        assertThat(orderItems.isEmpty());
    }

    @Test
    public void testRemoveItemFromOrderSuccess() {
        Order o = new Order();
        o.setId(1);
        Product p = new Product();
        p.setId(1);
        OrderItem oi = new OrderItem();
        oi.setId(1);
        oi.setProductId(p.getId());
        oi.setOrderId(p.getId());
        List<OrderItem> list = new ArrayList<>();
        list.add(oi);

        when(orderItemService.findByOrderIdAndProductId(o.getId(),p.getId())).thenReturn(oi);
        when(orderItemService.deleteById(any(Integer.class))).thenReturn(true);
        when(orderItemService.findByOrderId(o.getId())).thenReturn(new ArrayList<>());
        List<OrderItem> listAfterDelete = orderService.removeItemFromOrder(o,p);
        assertThat(list.isEmpty());
    }

    @Test
    public void testRemoveItemFromOrderFailed() {
        List<OrderItem> orderItems = orderService.removeItemFromOrder(null,null);
        assertThat(orderItems.isEmpty());
    }

    @Test
    public void testGetAllSuccess() {
        Order o = new Order();
        o.setId(1);
        List<Order> list = new ArrayList<>();
        list.add(o);
        when(orderRepository.findAll()).thenReturn(list);
        List<Order> returnedList = orderService.getAll();
        assertThat(returnedList.contains(o));
    }

    @Test
    public void testGetAllFailure() {
        when(orderRepository.findAll()).thenReturn(null);
        List<Order> returnedList = orderService.getAll();
        assertThat(returnedList.isEmpty());
    }

}

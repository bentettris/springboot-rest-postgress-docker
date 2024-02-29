package com.example.demo.unit.controller;

import com.example.demo.controller.OrderController;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    @Test
    public void testGetOrderByIdSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Order o = new Order();
        when(orderService.getOrderById(any(Integer.class))).thenReturn(o);
        ResponseEntity<Order> responseEntity = orderController.getOrderById(Optional.of(1));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).isEqualTo(o);
    }

    @Test
    public void testGetOrderByIdFailureNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(orderService.getOrderById(any(Integer.class))).thenReturn(null);
        ResponseEntity<Order> responseEntity = orderController.getOrderById(Optional.of(1));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void testGetOrderByIdFailureInternalServerError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Order> responseEntity = orderController.getOrderById(Optional.empty());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testGetAllOrdersSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Order o1 = new Order();
        Order o2 = new Order();
        List<Order> orderList = new ArrayList<>();
        orderList.add(o1);
        orderList.add(o2);

        when(orderService.getAll()).thenReturn(orderList);
        ResponseEntity<List<Order>> responseEntity = orderController.getAllOrders();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).contains(o1);
        assertThat(responseEntity.getBody()).contains(o1);
    }

    @Test
    public void testGetAllOrdersFailureNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(orderService.getAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Order>> responseEntity = orderController.getAllOrders();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void testGetAllOrdersFailureInternalServerError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(orderService.getAll()).thenReturn(null);
        ResponseEntity<List<Order>> responseEntity = orderController.getAllOrders();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testCreateOrderSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Order o = new Order();
        when(orderService.createOrder(any(Order.class))).thenReturn(o);
        ResponseEntity<Order> responseEntity = orderController.createOrder(o);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).isEqualTo(o);
    }

    @Test
    public void testCreateOrderFailureInternalServiceError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Order o = new Order();
        when(orderService.createOrder(any(Order.class))).thenReturn(null);
        ResponseEntity<Order> responseEntity = orderController.createOrder(o);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testCreateOrderFailureBadRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Order> responseEntity = orderController.createOrder(null);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    public void testUpdateOrderSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Order o1 = new Order();
        o1.setId(1);
        o1.setShippingCity("Eisenach");
        Order o2 = new Order();
        o2.setId(1);
        o2.setShippingCity("Brandenburg");
        when(orderService.getOrderById(1)).thenReturn(o1);
        when(orderService.updateOrder(o1)).thenReturn(o2);
        ResponseEntity<Order> responseEntity = orderController.updateOrder(o1.getId(),o1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody().equals(o2));
    }

    @Test
    public void testUpdateOrderFailureNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Order o1 = new Order();
        o1.setId(1);
        o1.setShippingCity("Eisenach");
        when(orderService.getOrderById(1)).thenReturn(null);
        ResponseEntity<Order> responseEntity = orderController.updateOrder(o1.getId(),o1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void testUpdateOrderFailureInternalServiceError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Order o1 = new Order();
        o1.setId(1);
        o1.setShippingCity("Eisenach");
        when(orderService.getOrderById(1)).thenReturn(o1);
        when(orderService.updateOrder(o1)).thenReturn(null);
        ResponseEntity<Order> responseEntity = orderController.updateOrder(o1.getId(),o1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testDeleteOrderSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Order o = new Order();
        o.setId(1);
        when(orderService.deleteById(o.getId())).thenReturn(true);
        ResponseEntity<OrderItem> responseEntity = orderController.deleteOrder(o.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    public void testDeleteOrderFailureInternalServiceError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Order o = new Order();
        o.setId(1);
        when(orderService.deleteById(o.getId())).thenReturn(false);
        ResponseEntity<OrderItem> responseEntity = orderController.deleteOrder(o.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

}

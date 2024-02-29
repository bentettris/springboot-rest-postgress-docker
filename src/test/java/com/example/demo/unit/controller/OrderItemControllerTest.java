package com.example.demo.unit.controller;

import com.example.demo.controller.OrderItemController;
import com.example.demo.model.OrderItem;
import com.example.demo.service.OrderItemService;
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

public class OrderItemControllerTest {

    @InjectMocks
    OrderItemController orderItemController;

    @Mock
    OrderItemService orderItemService;

    @Test
    public void testGetOrderItemByIdSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        OrderItem orderItem = new OrderItem();
        when(orderItemService.findById(any(Integer.class))).thenReturn(orderItem);
        ResponseEntity<OrderItem> responseEntity = orderItemController.getOrderItemById(Optional.of(1));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).isEqualTo(orderItem);
    }

    @Test
    public void testGetOrderItemByIdFailureNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(orderItemService.findById(any(Integer.class))).thenReturn(null);
        ResponseEntity<OrderItem> responseEntity = orderItemController.getOrderItemById(Optional.of(1));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void testGetOrderItemByIdFailureInternalServerError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<OrderItem> responseEntity = orderItemController.getOrderItemById(Optional.empty());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testGetAllOrderItemsSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        OrderItem oi1 = new OrderItem();
        OrderItem oi2 = new OrderItem();
        List orderItemList = new ArrayList<OrderItem>();
        orderItemList.add(oi1);
        orderItemList.add(oi2);

        when(orderItemService.getAll()).thenReturn(orderItemList);
        ResponseEntity<List<OrderItem>> responseEntity = orderItemController.getAllOrderItems();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).contains(oi1);
        assertThat(responseEntity.getBody()).contains(oi2);
    }

    @Test
    public void testGetAllOrderItemsFailureNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(orderItemService.getAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<OrderItem>> responseEntity = orderItemController.getAllOrderItems();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void testGetAllOrderItemsFailureInternalServerError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(orderItemService.getAll()).thenReturn(null);
        ResponseEntity<List<OrderItem>> responseEntity = orderItemController.getAllOrderItems();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testCreateOrderItemSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        OrderItem oi = new OrderItem();
        when(orderItemService.create(any(OrderItem.class))).thenReturn(oi);
        ResponseEntity<OrderItem> responseEntity = orderItemController.createOrderItem(oi);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).isEqualTo(oi);
    }

    @Test
    public void testCreateOrderItemFailureInternalServerError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(orderItemService.create(any(OrderItem.class))).thenReturn(null);
        ResponseEntity<OrderItem> responseEntity = orderItemController.createOrderItem(new OrderItem());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testCreateOrderItemFailureBadRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<OrderItem> responseEntity = orderItemController.createOrderItem(null);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    public void testUpdateOrderItemSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        OrderItem o1 = new OrderItem();
        o1.setId(1);
        o1.setQuantity(1);
        OrderItem o2 = new OrderItem();
        o2.setId(1);
        o2.setQuantity(2);
        when(orderItemService.findById(1)).thenReturn(o1);
        when(orderItemService.update(o1)).thenReturn(o2);
        ResponseEntity<OrderItem> responseEntity = orderItemController.updateOrderItem(o1.getId(), o1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody().equals(o2));
    }

    @Test
    public void testUpdateOrderItemFailureNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        OrderItem o1 = new OrderItem();
        o1.setId(1);
        o1.setQuantity(1);
        when(orderItemService.findById(1)).thenReturn(null);
        ResponseEntity<OrderItem> responseEntity = orderItemController.updateOrderItem(o1.getId(), o1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void testUpdateOrderItemFailureInternalServerError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        OrderItem o1 = new OrderItem();
        o1.setId(1);
        o1.setQuantity(1);
        when(orderItemService.findById(1)).thenReturn(o1);
        when(orderItemService.update(o1)).thenReturn(null);
        ResponseEntity<OrderItem> responseEntity = orderItemController.updateOrderItem(o1.getId(), o1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testDeleteOrderItemSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        OrderItem oi = new OrderItem();
        oi.setId(1);
        when(orderItemService.deleteById(oi.getId())).thenReturn(true);
        ResponseEntity<OrderItem> responseEntity = orderItemController.deleteOrderItem(oi.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    public void testDeleteOrderItemFailureInternalServerError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        OrderItem oi = new OrderItem();
        oi.setId(1);
        when(orderItemService.deleteById(oi.getId())).thenReturn(false);
        ResponseEntity<OrderItem> responseEntity = orderItemController.deleteOrderItem(oi.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }


}

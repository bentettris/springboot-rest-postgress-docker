package com.example.demo.unit.service;

import com.example.demo.db.OrderItemRepository;
import com.example.demo.model.OrderItem;
import com.example.demo.service.OrderItemService;
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
public class OrderItemServiceTest {

    @InjectMocks
    OrderItemService orderItemService;

    @Mock
    OrderItemRepository orderItemRepository;

    @Test
    public void testCreate() {
        OrderItem o = new OrderItem();
        when(orderItemRepository.save(o)).thenReturn(o);
        OrderItem oi = orderItemService.create(o);
        assertThat(o).isEqualTo(o);
    }

    @Test
    public void testUpdate() {
        OrderItem o = new OrderItem();
        OrderItem o2 = new OrderItem();
        when(orderItemRepository.save(o)).thenReturn(o2);
        OrderItem oReturned = orderItemService.update(o);
        assertThat(oReturned).isEqualTo(o2);
    }

    @Test
    public void testDeleteReturnTrue() {
        OrderItem o = new OrderItem();
        o.setId(1);
        o.setCreated(new Date());
        o.setModified(new Date());
        o.setOrderId(1);
        o.setProductId(1);
        o.setQuantity(10);
        when(orderItemRepository.findById(1)).thenReturn(Optional.of(o));
        doNothing().when(orderItemRepository).delete(o);
        Boolean ans = orderItemService.deleteById(o.getId());
        assertThat(ans).isEqualTo(true);
    }

    @Test
    public void testDeleteReturnFalse() {
        OrderItem o = new OrderItem();
        Boolean ans = orderItemService.deleteById(1);
        assertThat(ans).isEqualTo(false);
    }

    @Test
    public void testDeleteAllReturnTrue() {
        OrderItem o = new OrderItem();
        List<OrderItem> list = new ArrayList<>();
        list.add(o);
        doNothing().when(orderItemRepository).deleteAll(list);
        Boolean ans = orderItemService.deleteAll(list);
        assertThat(ans).isEqualTo(true);
    }

    @Test
    public void testDeleteAllReturnFalse() {
        Boolean ans = orderItemService.deleteAll(null);
        assertThat(ans).isEqualTo(false);
    }

    @Test
    public void findByIdSuccess() {
        OrderItem oi = new OrderItem();
        when(orderItemRepository.findById(any(Integer.class))).thenReturn(Optional.of(oi));
        OrderItem oReturned = orderItemService.findById(1);
        assertThat(oReturned).isEqualTo(oi);
    }

    @Test
    public void findByIdFailure() {
        assertThatThrownBy( () -> orderItemService.findById(null)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void findByProductIdSuccess() {
        OrderItem oi = new OrderItem();
        List<OrderItem> oiList = new ArrayList<>();
        oiList.add(oi);
        when(orderItemRepository.findByProductId(any(Integer.class))).thenReturn(oiList);
        List<OrderItem> list = orderItemService.findByProductId(1);
        assertThat(list.contains(oi));
    }

    @Test
    public void findByProductIdFailure() {
        OrderItem oi = new OrderItem();
        List<OrderItem> oiList = new ArrayList<>();
        when(orderItemRepository.findByProductId(any(Integer.class))).thenReturn(oiList);
        List<OrderItem> list = orderItemService.findByProductId(1);
        assertThat(!list.contains(oi));
    }

    @Test
    public void findByOrderIdSuccess() {
        OrderItem oi = new OrderItem();
        List<OrderItem> oiList = new ArrayList<>();
        oiList.add(oi);
        when(orderItemRepository.findByOrderId(any(Integer.class))).thenReturn(oiList);
        List<OrderItem> list = orderItemService.findByOrderId(1);
        assertThat(list.contains(oi));
    }

    @Test
    public void findByOrderIdFailure() {
        OrderItem oi = new OrderItem();
        List<OrderItem> oiList = new ArrayList<>();
        when(orderItemRepository.findByOrderId(any(Integer.class))).thenReturn(oiList);
        List<OrderItem> list = orderItemService.findByOrderId(1);
        assertThat(!list.contains(oi));
    }

    @Test
    public void testFindByOrderIdAndProductIdSuccess() {
        OrderItem oi = new OrderItem();
        when(orderItemRepository.findByOrderIdAndProductId(any(Integer.class), any(Integer.class))).thenReturn(oi);
        OrderItem oReturned = orderItemService.findByOrderIdAndProductId(1,1);
        assertThat(oReturned).isEqualTo(oi);
    }

    @Test
    public void testFindByOrderIdAndProductIdFailure() {
        OrderItem oi = new OrderItem();
        when(orderItemRepository.findByOrderIdAndProductId(any(Integer.class), any(Integer.class))).thenReturn(null);
        OrderItem oReturned = orderItemService.findByOrderIdAndProductId(1,1);
        assertThat(oReturned).isNull();
    }

    @Test
    public void testGetAllSuccess() {
        OrderItem o1 = new OrderItem();
        OrderItem o2 = new OrderItem();

        List<OrderItem> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        when(orderItemRepository.findAll()).thenReturn(list);
        List listReturned = orderItemService.getAll();
        assertThat(listReturned.contains(o1));
        assertThat(listReturned.contains(o2));
    }

    @Test
    public void testGetAllFailure() {
        OrderItem o1 = new OrderItem();
        OrderItem o2 = new OrderItem();

        List<OrderItem> list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        when(orderItemRepository.findAll()).thenReturn(null);
        List listReturned = orderItemService.getAll();
        assertThat(listReturned.isEmpty());
    }


}

package com.example.demo.unit.db;

import com.example.demo.db.OrderRepository;
import com.example.demo.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    Order o1;
    Order o2;

    @BeforeEach
    public void dataSetup() {

        o1 = new Order();
        o1.setShippingCity("Washington");
        o1.setShippingRegion("DC");
        o1.setShippingCountry("USA");
        o1.setShippingAddressLine1("1000 G ST NW");
        o1.setShippingPostalCode("20001");
        o1.setCreated(new Date());
        o1.setModified(new Date());

        o2 = new Order();
        o2.setShippingCity("Brooklyn");
        o2.setShippingRegion("NY");
        o2.setShippingCountry("USA");
        o2.setShippingAddressLine1("170 Clinton Ave");
        o2.setShippingPostalCode("11205");
        o2.setCreated(new Date());
        o2.setModified(new Date());
    }

    @Test
    public void testSave() {
        Order savedo1 = orderRepository.save(o1);
        assertThat(savedo1).isNotNull();
        assertThat(savedo1.getId()).isNotNull();
    }

    @Test
    public void testDelete() {
        Order savedo1 = orderRepository.save(o1);
        Integer id = savedo1.getId();
        orderRepository.delete(savedo1);
        assertThatThrownBy( () -> orderRepository.findById(id).get()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testFindById() {
        Order savedo1 = orderRepository.save(o1);
        Order retrieved = orderRepository.findById(savedo1.getId()).get();
        assertThat(savedo1).isEqualTo(retrieved);
    }

    @Test
    public void testFindAll() {
        Order savedo1 = orderRepository.save(o1);
        Order savedo2 = orderRepository.save(o2);

        Iterable<Order> it = orderRepository.findAll();
        List<Order> list = (List<Order>) StreamSupport.stream(it.spliterator(), false)
                .collect(Collectors.toList());
        assertThat(list.contains(savedo2));
        assertThat(list.contains(savedo1));
    }
}

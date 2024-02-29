package com.example.demo.unit.db;

import com.example.demo.db.OrderItemRepository;
import com.example.demo.db.OrderRepository;
import com.example.demo.db.ProductRepository;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class OrderItemRepositoryTest {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;
    Order o;
    Product p1;
    Product p2;
    OrderItem oi1;
    OrderItem oi2;

    @BeforeEach
    public void dataSetup() {

        o = new Order();
        o.setShippingCity("Washington");
        o.setShippingRegion("DC");
        o.setShippingCountry("USA");
        o.setShippingAddressLine1("1000 G ST NW");
        o.setShippingPostalCode("20001");
        o.setCreated(new Date());
        o.setModified(new Date());
        o = orderRepository.save(o);

        p1 = new Product();
        p1.setName("Product 1");
        p1.setPrice("5.00 USD");
        p1.setSku("001");
        p1.setDescription("Our very first product");
        p1.setCreated(new Date());
        p1.setModified(new Date());
        p1 = productRepository.save(p1);

        p2 = new Product();
        p2.setName("Product 2");
        p2.setPrice("15.00 USD");
        p2.setSku("002");
        p2.setDescription("Our second product");
        p2.setCreated(new Date());
        p2.setModified(new Date());
        p2 = productRepository.save(p2);

        oi1 = new OrderItem();
        oi1.setOrderId(o.getId());
        oi1.setProductId(p1.getId());
        oi1.setQuantity(3);
        oi1.setCreated(new Date());
        oi1.setModified(new Date());

        oi2 = new OrderItem();
        oi2.setOrderId(o.getId());
        oi2.setProductId(p2.getId());
        oi2.setQuantity(3);
        oi2.setCreated(new Date());
        oi2.setModified(new Date());
    }

    @AfterEach
    public void tearDownData() {
        orderItemRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void testSave() {
        OrderItem saved = orderItemRepository.save(oi1);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void testDelete() {
        OrderItem saved = orderItemRepository.save(oi1);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();

        orderItemRepository.delete(saved);
        Optional<OrderItem>  result = orderItemRepository.findById(oi1.getId());
        assertThat(result.isEmpty());
    }

    @Test
    public void testDeleteAll() {
        OrderItem saved = orderItemRepository.save(oi1);
        OrderItem saved2 = orderItemRepository.save(oi2);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved2).isNotNull();
        assertThat(saved2.getId()).isNotNull();
        orderItemRepository.deleteAll();
        Optional<OrderItem>  result = orderItemRepository.findById(oi1.getId());
        Optional<OrderItem>  result2 = orderItemRepository.findById(oi2.getId());
        assertThat(result.isEmpty());
        assertThat(result2.isEmpty());
    }

    @Test
    public void testFindById() {
        OrderItem saved = orderItemRepository.save(oi1);
        OrderItem retrieved = orderItemRepository.findById(oi1.getId()).get();
        assertThat(saved).isEqualTo(retrieved);
    }

    @Test
    public void testFindByProductId() {
        OrderItem saved = orderItemRepository.save(oi1);
        List<OrderItem> found = orderItemRepository.findByProductId(p1.getId());
        assertThat(found.contains(saved));
    }

    @Test
    public void testFindByOrderId() {
        OrderItem saved = orderItemRepository.save(oi1);
        List<OrderItem> found = orderItemRepository.findByOrderId(o.getId());
        assertThat(found.contains(saved));
    }

    @Test
    public void testFindByOrderIdAndProductId() {
        OrderItem saved = orderItemRepository.save(oi1);
        OrderItem found = orderItemRepository.findByOrderIdAndProductId(o.getId(),p1.getId());
        assertThat(found).isEqualTo(saved);
    }

    @Test
    public void testGetAll() {
        OrderItem saved = orderItemRepository.save(oi1);
        OrderItem saved2 = orderItemRepository.save(oi2);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved2).isNotNull();
        assertThat(saved2.getId()).isNotNull();
        Iterable<OrderItem> foundIterable = orderItemRepository.findAll();
        List<OrderItem> foundList = (List<OrderItem>) StreamSupport.stream(foundIterable.spliterator(), false)
                .collect(Collectors.toList());

        assertThat(foundList.contains(saved));
        assertThat(foundList.contains(saved2));
    }
}

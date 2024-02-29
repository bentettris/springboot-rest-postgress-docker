package com.example.demo.unit.db;

import com.example.demo.db.ProductRepository;
import com.example.demo.model.Product;
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
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    Product p1;
    Product p2;

    @BeforeEach
    public void setupTestData() {
        p1 = new Product();
        p1.setName("Product 1");
        p1.setPrice("5.00 USD");
        p1.setSku("001");
        p1.setDescription("Our very first product");
        p1.setCreated(new Date());
        p1.setModified(new Date());

        p2 = new Product();
        p2.setName("Product 2");
        p2.setPrice("15.00 USD");
        p2.setSku("002");
        p2.setDescription("Our second product");
        p2.setCreated(new Date());
        p2.setModified(new Date());

    }

    @Test
    public void testSave() {
        Product savedP1 = productRepository.save(p1);
        assertThat(savedP1).isNotNull();
        assertThat(savedP1.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Product savedP1 = productRepository.save(p1);
        Product retrieved = productRepository.findById(savedP1.getId()).get();
        assertThat(savedP1).isEqualTo(retrieved);
    }

    @Test
    public void testDelete() {
        Product savedP1 = productRepository.save(p1);
        Integer id = savedP1.getId();
        productRepository.delete(savedP1);
        assertThatThrownBy( () -> productRepository.findById(id).get()).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testFindAll() {
        Product savedP1 = productRepository.save(p1);
        Product savedP2 = productRepository.save(p2);

        Iterable<Product> it = productRepository.findAll();
        List<Product> list = (List<Product>) StreamSupport.stream(it.spliterator(), false)
                .collect(Collectors.toList());
        assertThat(list.contains(savedP2));
        assertThat(list.contains(savedP1));
    }
}

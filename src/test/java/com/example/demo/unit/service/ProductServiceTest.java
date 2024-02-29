package com.example.demo.unit.service;

import com.example.demo.db.ProductRepository;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
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
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void testCreate() {
        Product p = new Product();
        p.setSku("0001");
        p.setName("Product 1");
        p.setDescription("Description of Product 1");
        p.setPrice("100.00 USD");
        p.setId(1);
        p.setCreated(new Date());
        p.setModified(new Date());
        when(productRepository.save(p)).thenReturn(p);
        Product savedP = productService.create(p);
        assertThat(p).isEqualTo(savedP);
    }

    @Test
    public void testRetrieveByIdSuccess() {
        Product p = new Product();
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(p));
        Product returnedP = productService.retrieveById(1);
        assertThat(p).isEqualTo(returnedP);
    }

    @Test
    public void testRetrieveByIdFailed() {
        assertThatThrownBy( () -> productService.retrieveById(null)).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testUpdate() {
        Product p = new Product();
        Product p2 = new Product();
        when(productRepository.save(p)).thenReturn(p2);
        Product oReturned = productService.update(p);
        assertThat(oReturned).isEqualTo(p2);
    }

    @Test
    public void testDeleteReturnTrue() {
        Product p = new Product();
        p.setId(1);
        doNothing().when(productRepository).delete(p);
        when(productRepository.findById(1)).thenReturn(Optional.of(p));
        Boolean ans = productService.deleteById(p.getId());
        assertThat(ans).isEqualTo(true);
    }

    @Test
    public void testDeleteReturnFalse() {
        Boolean ans = productService.deleteById(null);
        assertThat(ans).isEqualTo(false);
    }

    @Test
    public void testGetAllSuccess() {
        Product p = new Product();
        p.setId(1);
        List<Product> list = new ArrayList<>();
        list.add(p);
        when(productRepository.findAll()).thenReturn(list);
        List<Product> returnedList = productService.getAll();
        assertThat(returnedList.contains(p));
    }

    @Test
    public void testGetAllFailure() {
        when(productRepository.findAll()).thenReturn(null);
        List<Product> returnedList = productService.getAll();
        assertThat(returnedList.isEmpty());
    }

}

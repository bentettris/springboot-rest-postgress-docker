package com.example.demo.unit.controller;

import com.example.demo.controller.ProductController;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
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
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @Test
    public void testAddProductSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product = new Product();
        when(productService.create(any(Product.class))).thenReturn(product);
        ResponseEntity<Product> responseEntity = productController.createProduct(product);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).isEqualTo(product);
    }

    @Test
    public void testAddProductFailedInternalServerError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product = new Product();
        when(productService.create(product)).thenReturn(null);
        ResponseEntity<Product> responseEntity = productController.createProduct(product);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testAddProductFailedBadRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Product> responseEntity = productController.createProduct(null);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    public void testGetProductByIdSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product = new Product();
        when(productService.retrieveById(any(Integer.class))).thenReturn(product);
        ResponseEntity<Product> responseEntity = productController.getProductById(Optional.of(1));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).isEqualTo(product);
    }

    @Test
    public void testGetProductByIdFailureInternalServerError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product = new Product();
        ResponseEntity<Product> responseEntity = productController.getProductById(Optional.empty());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testGetProductByIdFailureNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(productService.retrieveById(any(Integer.class))).thenReturn(null);
        ResponseEntity<Product> responseEntity = productController.getProductById(Optional.of(1));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void testGetAllProductsSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product1 = new Product();
        Product product2 = new Product();
        List productList = new ArrayList<Product>();
        productList.add(product1);
        productList.add(product2);

        when(productService.getAll()).thenReturn(productList);
        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(responseEntity.getBody()).contains(product1);
        assertThat(responseEntity.getBody()).contains(product2);
    }

    @Test
    public void testGetAllProductsFailureNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product1 = new Product();
        Product product2 = new Product();
        List productList = new ArrayList<Product>();
        productList.add(product1);
        productList.add(product2);

        when(productService.getAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Test
    public void testGetAllProductsFailureInternalServerError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product1 = new Product();
        Product product2 = new Product();
        List productList = new ArrayList<Product>();
        productList.add(product1);
        productList.add(product2);

        when(productService.getAll()).thenReturn(null);
        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testUpdateProductSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product = new Product();
        product.setId(1);
        when(productService.retrieveById(1)).thenReturn(product);
        when(productService.update(product)).thenReturn(product);
        ResponseEntity<Product> responseEntity = productController.updateProduct(product.getId(), product);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    public void testUpdateProductFailureInternalServiceError() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product = new Product();
        product.setId(1);
        when(productService.retrieveById(1)).thenReturn(product);
        ResponseEntity<Product> responseEntity = productController.updateProduct(product.getId(), product);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

    @Test
    public void testUpdateProductFailureNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product = new Product();
        product.getId();
        ResponseEntity<Product> responseEntity = productController.updateProduct(product.getId(), product);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }


    @Test
    public void testDeleteProductSuccess() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product = new Product();
        product.setId(1);
        when(productService.deleteById(product.getId())).thenReturn(true);
        ResponseEntity<Product> responseEntity = productController.deleteProduct(product.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    public void testDeleteProductFailure() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Product product = new Product();
        when(productService.deleteById(any(Integer.class))).thenReturn(false);
        ResponseEntity<Product> responseEntity = productController.deleteProduct(1);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(500));
    }

}

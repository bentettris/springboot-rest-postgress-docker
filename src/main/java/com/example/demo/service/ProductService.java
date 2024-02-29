package com.example.demo.service;

import com.example.demo.db.OrderItemRepository;
import com.example.demo.db.ProductRepository;
import com.example.demo.model.Product;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepo;

    @Autowired
    OrderItemRepository orderItemRepository;

    public Product create(Product p) {
        return productRepo.save(p);
    }

    public Product retrieveById(Integer id) throws NoSuchElementException {
        return productRepo.findById(id).orElseThrow();
    }

    public Product update(Product p) {
        return productRepo.save(p);
    }

    public boolean deleteById(Integer id) {
        Optional<Product> pOptional = productRepo.findById(id);
        Product p = null;
        if(pOptional.isPresent()) p = pOptional.get();
        if(p == null) {
            return false;
        }
        productRepo.delete(p);
        return true;
    }

    public List<Product> getAll() {
        Iterable iterator = productRepo.findAll();
        List<Product> listToReturn = new ArrayList<>();
        try{
            listToReturn = (List<Product>) StreamSupport.stream(iterator.spliterator(), false)
                    .collect(Collectors.toList());
            return listToReturn;
        }
        catch(Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            return listToReturn;
        }
    }
}

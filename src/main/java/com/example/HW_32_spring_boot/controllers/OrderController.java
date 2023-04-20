package com.example.HW_32_spring_boot.controllers;


import com.example.HW_32_spring_boot.entity.Order;
import com.example.HW_32_spring_boot.entity.Product;
import com.example.HW_32_spring_boot.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;


    @PostConstruct
    public void initDate() {
        Product product1 = new Product(1L, "product1", 10.0);
        Product product2 = new Product(2L, "product2", 20.0);
        Product product3 = new Product(3L, "product3", 30.0);
        Product product4 = new Product(4L, "product4", 40.0);
        Product product5 = new Product(5L, "product5", 50.0);


        Order order1 = new Order(1L, LocalDate.now(), 100.0, Arrays.asList(product1, product2));
        Order order2 = new Order(2L, LocalDate.now().minusDays(1), 200.0, Arrays.asList(product3, product4, product5));

        orderRepository.save(order1);
        orderRepository.save(order2);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public Order addOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }
}

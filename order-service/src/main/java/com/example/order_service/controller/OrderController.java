package com.example.order_service.controller;

import com.example.order_service.dto.OrderResponseDTO;
import com.example.order_service.dto.ProductResponseDTO;
import com.example.order_service.entity.Order;

import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping("/placeorder")
    public Mono<ResponseEntity<OrderResponseDTO>> placeOrder(@RequestBody Order order) {

        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/products/{id}", order.getProductId())
                .retrieve()
                .bodyToMono(ProductResponseDTO.class)
                .map(product -> {

                    order.setName(product.getName());

                    Order savedOrder = orderRepository.save(order);

                    OrderResponseDTO response = new OrderResponseDTO();
                    response.setOrderId(savedOrder.getId());
                    response.setProductId(savedOrder.getProductId());
                    response.setQuantity(savedOrder.getQuantity());

                    response.setProductName(product.getName());
                    response.setPrice(product.getPrice().doubleValue());
                    response.setTotalPrice(savedOrder.getQuantity() * product.getPrice());

                    return ResponseEntity.ok(response);
                });
    }

    @GetMapping()
    public List<Order> getall(){
        return orderRepository.findAll();
    }
}

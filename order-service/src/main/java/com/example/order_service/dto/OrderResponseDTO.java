package com.example.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OrderResponseDTO {

    private Long OrderId;
    private Long productId;
    private int quantity;
    //Product Details
    private String productName;
    private Double Price;
    public Integer TotalPrice;



}

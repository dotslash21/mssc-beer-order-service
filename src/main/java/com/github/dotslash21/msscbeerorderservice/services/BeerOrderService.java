package com.github.dotslash21.msscbeerorderservice.services;

import org.springframework.data.domain.Pageable;
import com.github.dotslash21.msscbeerorderservice.web.model.BeerOrderDto;
import com.github.dotslash21.msscbeerorderservice.web.model.BeerOrderPagedList;

import java.util.UUID;

public interface BeerOrderService {

    BeerOrderPagedList listOrders(UUID customerId, Pageable pageable);

    BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto);

    BeerOrderDto getOrderById(UUID customerId, UUID orderId);

    void pickupOrder(UUID customerId, UUID orderId);
}

package com.github.dotslash21.msscbeerorderservice.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.github.dotslash21.msscbeerorderservice.services.BeerOrderService;
import com.github.dotslash21.msscbeerorderservice.web.model.BeerOrderDto;
import com.github.dotslash21.msscbeerorderservice.web.model.BeerOrderPagedList;

import java.util.UUID;

@RestController
@RequestMapping(BeerOrderController.API_BASE_PREFIX)
@RequiredArgsConstructor
public class BeerOrderController {

    public static final String API_BASE_PREFIX = "/api/v1/customers/{customerId}";
    public static final String ORDERS_ENDPOINT = "/orders";
    public static final String ORDER_ID_ENDPOINT = "/{orderId}";
    private static final String PICKUP_ENDPOINT = "/pickup";

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerOrderService beerOrderService;

    @GetMapping(ORDERS_ENDPOINT)
    public BeerOrderPagedList listOrders(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return beerOrderService.listOrders(
                customerId, PageRequest.of(pageNumber, pageSize));
    }

    @PostMapping(ORDERS_ENDPOINT)
    @ResponseStatus(HttpStatus.CREATED)
    public BeerOrderDto placeOrder(
            @PathVariable("customerId") UUID customerId,
            @RequestBody BeerOrderDto beerOrderDto) {

        return beerOrderService.placeOrder(customerId, beerOrderDto);
    }

    @GetMapping(ORDERS_ENDPOINT + ORDER_ID_ENDPOINT)
    public BeerOrderDto getOrder(
            @PathVariable("customerId") UUID customerId,
            @PathVariable("orderId") UUID orderId) {

        return beerOrderService.getOrderById(customerId, orderId);
    }

    @PutMapping(ORDERS_ENDPOINT + ORDER_ID_ENDPOINT + PICKUP_ENDPOINT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupOrder(
            @PathVariable("customerId") UUID customerId,
            @PathVariable("orderId") UUID orderId) {

        beerOrderService.pickupOrder(customerId, orderId);
    }
}

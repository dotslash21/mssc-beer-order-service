package com.github.dotslash21.msscbeerorderservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.dotslash21.msscbeerorderservice.bootstrap.BeerOrderBootStrap;
import com.github.dotslash21.msscbeerorderservice.domain.Customer;
import com.github.dotslash21.msscbeerorderservice.repositories.CustomerRepository;
import com.github.dotslash21.msscbeerorderservice.web.model.BeerOrderDto;
import com.github.dotslash21.msscbeerorderservice.web.model.BeerOrderLineDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class TastingRoomService {

    private final CustomerRepository customerRepository;
    private final BeerOrderService beerOrderService;
    // private final BeerOrderRepository beerOrderRepository; // Will be used in the future

    private final List<String> beerUpcs = Stream
            .of(
                    BeerOrderBootStrap.BEER_1_UPC,
                    BeerOrderBootStrap.BEER_2_UPC,
                    BeerOrderBootStrap.BEER_3_UPC)
            .collect(Collectors.toList());

    @Transactional
    @Scheduled(fixedRate = 2000) //run every 2 seconds
    void placeTastingRoomOrder() {
        List<Customer> customerList = customerRepository.findAllByCustomerNameLike(BeerOrderBootStrap.TASTING_ROOM);

        if (customerList.size() == 1) { //should be just one
            doPlaceOrder(customerList.get(0));
        } else {
            log.error("Too many or too few tasting room customers found");
        }
    }

    private void doPlaceOrder(Customer customer) {
        String beerToOrder = getRandomBeerUpc();

        BeerOrderLineDto beerOrderLineDto = BeerOrderLineDto.builder()
                .upc(beerToOrder)
                .orderQuantity(new Random().nextInt(5) + 1) // TODO: Externalize value to property
                .build();

        List<BeerOrderLineDto> beerOrderLineDtoList = new ArrayList<>();
        beerOrderLineDtoList.add(beerOrderLineDto);

        BeerOrderDto beerOrderDto = BeerOrderDto.builder()
                .customerId(customer.getId())
                .customerRef(UUID.randomUUID().toString())
                .beerOrderLines(beerOrderLineDtoList)
                .build();

        BeerOrderDto savedOrder = beerOrderService.placeOrder(customer.getId(), beerOrderDto);

        log.info("Tasting room order placed for customer id: {} with order id: {}",
                customer.getId(), savedOrder.getId());
    }

    private String getRandomBeerUpc() {
        return beerUpcs.get(new Random().nextInt(beerUpcs.size()));
    }
}

package xyz.arunangshu.msscbeerorderservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.arunangshu.msscbeerorderservice.domain.BeerOrder;
import xyz.arunangshu.msscbeerorderservice.domain.Customer;
import xyz.arunangshu.msscbeerorderservice.domain.OrderStatusEnum;
import xyz.arunangshu.msscbeerorderservice.repositories.BeerOrderRepository;
import xyz.arunangshu.msscbeerorderservice.repositories.CustomerRepository;
import xyz.arunangshu.msscbeerorderservice.web.mappers.BeerOrderMapper;
import xyz.arunangshu.msscbeerorderservice.web.model.BeerOrderDto;
import xyz.arunangshu.msscbeerorderservice.web.model.BeerOrderPagedList;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerOrderMapper beerOrderMapper;
    // private final ApplicationEventPublisher publisher; // Will be used in the future

    @Override
    public BeerOrderPagedList listOrders(UUID customerId, Pageable pageable) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Page<BeerOrder> beerOrderPage =
                    beerOrderRepository.findAllByCustomer(customerOptional.get(), pageable);

            return new BeerOrderPagedList(
                    beerOrderPage
                            .stream()
                            .map(beerOrderMapper::beerOrderToDto)
                            .collect(Collectors.toList()),
                    PageRequest.of(
                            beerOrderPage.getPageable().getPageNumber(),
                            beerOrderPage.getPageable().getPageSize()),
                    beerOrderPage.getTotalElements());
        }

        return null;
    }

    @Transactional
    @Override
    public BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            BeerOrder beerOrder = beerOrderMapper.dtoToBeerOrder(beerOrderDto);
            beerOrder.setId(null);
            beerOrder.setCustomer(customerOptional.get());
            beerOrder.setOrderStatus(OrderStatusEnum.NEW);

            beerOrder.getBeerOrderLines().forEach(line -> line.setBeerOrder(beerOrder));

            BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

            // TODO: Impl
            // publisher.publishEvent(new BeerOrderCreatedEvent(this, savedBeerOrder.getId()));

            return beerOrderMapper.beerOrderToDto(savedBeerOrder);
        }

        // TODO: Add new exception type
        throw new RuntimeException("Customer Not Found");
    }

    @Override
    public BeerOrderDto getOrderById(UUID customerId, UUID orderId) {
        return beerOrderMapper.beerOrderToDto(getOrder(customerId, orderId));
    }

    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        BeerOrder beerOrder = getOrder(customerId, orderId);
        beerOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);

        beerOrderRepository.save(beerOrder);
    }

    private BeerOrder getOrder(UUID customerId, UUID orderId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);

            if (beerOrderOptional.isPresent()) {
                BeerOrder beerOrder = beerOrderOptional.get();

                // Fall to exception if customer's id does not match - order not for customer
                if (beerOrder.getCustomer().getId().equals(customerId)) {
                    return beerOrder;
                }
            }

            throw new RuntimeException("Beer Order Not Found");
        }

        throw new RuntimeException("Customer Not Found");
    }
}

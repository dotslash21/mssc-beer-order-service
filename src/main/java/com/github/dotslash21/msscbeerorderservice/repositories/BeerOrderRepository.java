package com.github.dotslash21.msscbeerorderservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import com.github.dotslash21.msscbeerorderservice.domain.BeerOrder;
import com.github.dotslash21.msscbeerorderservice.domain.Customer;
import com.github.dotslash21.msscbeerorderservice.domain.OrderStatusEnum;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {

    Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);

    List<BeerOrder> findAllByOrderStatus(OrderStatusEnum orderStatus);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    BeerOrder findOneById(UUID id);
}

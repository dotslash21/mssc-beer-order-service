package com.github.dotslash21.msscbeerorderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.dotslash21.msscbeerorderservice.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findAllByCustomerNameLike(String customerName);
}

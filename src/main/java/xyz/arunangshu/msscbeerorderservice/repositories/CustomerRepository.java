package xyz.arunangshu.msscbeerorderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.arunangshu.msscbeerorderservice.domain.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    List<Customer> findAllByCustomerNameLike(String customerName);
}

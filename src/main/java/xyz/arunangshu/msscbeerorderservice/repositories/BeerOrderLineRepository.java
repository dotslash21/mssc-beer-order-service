package xyz.arunangshu.msscbeerorderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.arunangshu.msscbeerorderservice.domain.BeerOrderLine;

import java.util.UUID;

public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLine, UUID> {
}

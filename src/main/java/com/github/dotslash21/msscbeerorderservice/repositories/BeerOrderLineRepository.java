package com.github.dotslash21.msscbeerorderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.dotslash21.msscbeerorderservice.domain.BeerOrderLine;

import java.util.UUID;

public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLine, UUID> {
}

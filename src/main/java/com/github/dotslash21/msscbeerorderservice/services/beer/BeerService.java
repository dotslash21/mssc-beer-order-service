package com.github.dotslash21.msscbeerorderservice.services.beer;

import com.github.dotslash21.msscbeerorderservice.services.beer.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    Optional<BeerDto> getBeerById(UUID beerId);

    Optional<BeerDto> getBeerByUpc(String upc);
}

package com.github.dotslash21.msscbeerorderservice.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.github.dotslash21.msscbeerorderservice.domain.BeerOrder;
import com.github.dotslash21.msscbeerorderservice.web.model.BeerOrderDto;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto beerOrderDto);
}

package com.github.dotslash21.msscbeerorderservice.web.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import com.github.dotslash21.msscbeerorderservice.domain.BeerOrderLine;
import com.github.dotslash21.msscbeerorderservice.web.model.BeerOrderLineDto;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {

    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine beerOrderLine);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto beerOrderLineDto);
}

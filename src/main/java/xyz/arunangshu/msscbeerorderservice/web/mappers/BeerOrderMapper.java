package xyz.arunangshu.msscbeerorderservice.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import xyz.arunangshu.msscbeerorderservice.domain.BeerOrder;
import xyz.arunangshu.msscbeerorderservice.web.model.BeerOrderDto;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto beerOrderDto);
}

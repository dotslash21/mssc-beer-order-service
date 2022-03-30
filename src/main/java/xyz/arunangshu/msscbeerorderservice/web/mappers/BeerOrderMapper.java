package xyz.arunangshu.msscbeerorderservice.web.mappers;

import org.mapstruct.Mapper;
import xyz.arunangshu.msscbeerorderservice.domain.BeerOrder;
import xyz.arunangshu.msscbeerorderservice.web.model.BeerOrderDto;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto beerOrderDto);
}

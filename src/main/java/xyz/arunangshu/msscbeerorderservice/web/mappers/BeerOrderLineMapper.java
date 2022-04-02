package xyz.arunangshu.msscbeerorderservice.web.mappers;

import org.mapstruct.Mapper;
import xyz.arunangshu.msscbeerorderservice.domain.BeerOrderLine;
import xyz.arunangshu.msscbeerorderservice.web.model.BeerOrderLineDto;

@Mapper(uses = {DateMapper.class})
public interface BeerOrderLineMapper {

    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine beerOrderLine);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto beerOrderLineDto);
}
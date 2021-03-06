package com.github.dotslash21.msscbeerorderservice.web.mappers;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.github.dotslash21.msscbeerorderservice.domain.BeerOrderLine;
import com.github.dotslash21.msscbeerorderservice.services.beer.BeerService;
import com.github.dotslash21.msscbeerorderservice.services.beer.model.BeerDto;
import com.github.dotslash21.msscbeerorderservice.web.model.BeerOrderLineDto;

import java.util.Optional;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

    @Setter(onMethod_ = {@Autowired})
    private BeerService beerService;

    @Setter(onMethod_ = {@Autowired, @Qualifier("delegate")})
    private BeerOrderLineMapper beerOrderLineMapper;

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine beerOrderLine) {
        BeerOrderLineDto beerOrderLineDto = beerOrderLineMapper.beerOrderLineToDto(beerOrderLine);
        Optional<BeerDto> beerDtoOptional = beerService.getBeerByUpc(beerOrderLine.getUpc());

        beerDtoOptional.ifPresent(beerDto -> {
            beerOrderLineDto.setBeerName(beerDto.getBeerName());
            beerOrderLineDto.setBeerStyle(beerDto.getBeerStyle().name());
            beerOrderLineDto.setPrice(beerDto.getPrice());
            beerOrderLineDto.setBeerId(beerDto.getId());
        });

        return beerOrderLineDto;
    }
}

package com.github.dotslash21.msscbeerorderservice.services.beer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.github.dotslash21.msscbeerorderservice.services.beer.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@ConfigurationProperties(prefix = "com.github.dotslash21.brewery")
@Component
public class BeerServiceImpl implements BeerService {

    public final String BEER_PATH_V1 = "/api/v1/beer";
    public final String BEER_UPC_PATH_V1 = "/api/v1/beerUpc/";

    private final RestTemplate restTemplate;

    private String beerServiceHost;

    public BeerServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<BeerDto> getBeerById(UUID beerId) {
        return Optional.ofNullable(
                restTemplate.getForObject(beerServiceHost + BEER_PATH_V1 + "/" + beerId.toString(), BeerDto.class));
    }

    @Override
    public Optional<BeerDto> getBeerByUpc(String upc) {
        return Optional.ofNullable(
                restTemplate.getForObject(beerServiceHost + BEER_UPC_PATH_V1 + "/" + upc, BeerDto.class));
    }

    public void setBeerServiceHost(String beerServiceHost) {
        this.beerServiceHost = beerServiceHost;
    }
}

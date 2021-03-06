package com.everis.bootcoin.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.bootcoin.model.CurrencyRate;

import reactor.core.publisher.Mono;


@Repository
public interface ICurrencyRateRepository extends ReactiveMongoRepository<CurrencyRate, UUID>{
	Mono<CurrencyRate> findFirstByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}

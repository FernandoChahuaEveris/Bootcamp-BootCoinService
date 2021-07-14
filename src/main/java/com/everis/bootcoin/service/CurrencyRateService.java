package com.everis.bootcoin.service;

import java.util.UUID;

import com.everis.bootcoin.model.CurrencyRate;

import reactor.core.publisher.Mono;

public interface CurrencyRateService extends ICRUD<CurrencyRate, UUID>{
	Mono<CurrencyRate> findFirstByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}

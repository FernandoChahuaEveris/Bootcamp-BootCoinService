package com.everis.bootcoin.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

import com.everis.bootcoin.model.CurrencyRate;
import com.everis.bootcoin.repository.ICurrencyRateRepository;

import com.everis.bootcoin.service.CurrencyRateService;

import reactor.core.publisher.Mono;
@Service
public class CurrencyRateServiceImpl extends CRUDImpl<CurrencyRate, UUID> implements CurrencyRateService {
	
	@Autowired
	private ICurrencyRateRepository repo;
	
	@Override
	protected ReactiveMongoRepository<CurrencyRate, UUID> getRepo() {
		return repo;
	}

	@Override
	public Mono<CurrencyRate> findFirstByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency) {
		return repo.findFirstByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
	}
}

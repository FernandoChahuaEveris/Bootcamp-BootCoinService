package com.everis.bootcoin.service;

import java.util.UUID;

import com.everis.bootcoin.dto.BootCoinMovementDTO;
import com.everis.bootcoin.model.BootCoinMovement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootCoinMovementService extends ICRUD<BootCoinMovement, UUID>{
	Mono<BootCoinMovement> publishOffer(BootCoinMovement body);

	Flux<BootCoinMovement> findAllOffers();


	Mono<BootCoinMovement> sell(UUID id, BootCoinMovementDTO body);
}

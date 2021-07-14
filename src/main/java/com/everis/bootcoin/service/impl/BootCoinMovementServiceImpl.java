package com.everis.bootcoin.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

import com.everis.bootcoin.dto.BootCoinMovementDTO;
import com.everis.bootcoin.model.BootCoinMovement;
import com.everis.bootcoin.repository.IBootCoinMovementRepository;
import com.everis.bootcoin.service.BootCoinMovementService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BootCoinMovementServiceImpl extends CRUDImpl<BootCoinMovement, UUID> implements BootCoinMovementService {
	
	@Autowired
	private IBootCoinMovementRepository repo;
	
	@Override
	protected ReactiveMongoRepository<BootCoinMovement, UUID> getRepo() {
		return repo;
	}
	@Override
	public Mono<BootCoinMovement> publishOffer(BootCoinMovement body){
		body.setId(UUID.randomUUID());
		body.setDateMovement(LocalDateTime.now());
		body.setTypeMovement("Purchase");
		body.setState("P");
		
		return repo.save(body);
	}
	
	@Override
	public Flux<BootCoinMovement> findAllOffers(){
		return repo.findAllByState("P");
	}
	
	@Override
	public Mono<BootCoinMovement> sell(UUID id, BootCoinMovementDTO body){
		
		return Mono.empty();
	}
}

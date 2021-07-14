package com.everis.bootcoin.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.bootcoin.model.BootCoinMovement;

import reactor.core.publisher.Flux;

@Repository
public interface IBootCoinMovementRepository extends ReactiveMongoRepository<BootCoinMovement, UUID>{
	Flux<BootCoinMovement> findAllByState(String state);
}

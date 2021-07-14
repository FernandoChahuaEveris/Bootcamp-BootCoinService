package com.everis.bootcoin.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.bootcoin.model.BootCoinAccount;

import reactor.core.publisher.Mono;

@Repository
public interface IBootCoinAccountRepository extends ReactiveMongoRepository<BootCoinAccount, UUID>{
	Mono<BootCoinAccount> findFirstByCellphoneNumber(String cellphoneNumber);
}

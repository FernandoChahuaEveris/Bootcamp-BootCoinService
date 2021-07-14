package com.everis.bootcoin.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

import com.everis.bootcoin.model.BootCoinAccount;
import com.everis.bootcoin.repository.IBootCoinAccountRepository;
import com.everis.bootcoin.service.BootCoinAccountService;

import reactor.core.publisher.Mono;

@Service
public class BootCoinAccountServiceImpl extends CRUDImpl<BootCoinAccount, UUID> implements BootCoinAccountService {
	
	@Autowired
	private IBootCoinAccountRepository repo;
	
	@Override
	protected ReactiveMongoRepository<BootCoinAccount, UUID> getRepo() {
		return repo;
	}
	
	@Override
	public Mono<BootCoinAccount> findByCellPhoneNumber(String cellphoneNumber){
		return repo.findFirstByCellphoneNumber(cellphoneNumber);
	}
	
}

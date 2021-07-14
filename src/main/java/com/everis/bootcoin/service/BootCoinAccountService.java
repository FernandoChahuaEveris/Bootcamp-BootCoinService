package com.everis.bootcoin.service;

import java.util.UUID;

import com.everis.bootcoin.model.BootCoinAccount;

import reactor.core.publisher.Mono;

public interface BootCoinAccountService extends ICRUD<BootCoinAccount, UUID>{

	Mono<BootCoinAccount> findByCellPhoneNumber(String cellphoneNumber);

}

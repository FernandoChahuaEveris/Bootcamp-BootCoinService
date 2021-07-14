package com.everis.bootcoin.service.impl;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.bootcoin.service.ICRUD;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CRUDImpl<T,ID> implements ICRUD<T,ID>{
	protected abstract ReactiveMongoRepository<T, ID> getRepo();
	
	@Override
	public Mono<T> create(T t){
		return getRepo().save(t);
	}
	
	@Override
	public Mono<T> update(T t){
		return getRepo().save(t);
	}
	
	@Override
	public Flux<T> findAll(){
		return getRepo().findAll();
	}
	
	@Override
	public Mono<T> findById(ID id){
		return getRepo().findById(id);
	}
	
	@Override
	public Mono<Void> deleteById(ID id){
		return getRepo().deleteById(id);
	}
}

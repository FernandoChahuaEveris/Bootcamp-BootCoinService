package com.everis.bootcoin.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bootcoin.model.BootCoinAccount;
import com.everis.bootcoin.service.BootCoinAccountService;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("bootcoin-account")
@RestController
public class BootCoinAccountController {
	@Autowired
	private BootCoinAccountService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<BootCoinAccount>>> findAll(){
		Flux<BootCoinAccount> fluxBCAccount = service.findAll();
		return Mono.just(ResponseEntity
							.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(fluxBCAccount));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<BootCoinAccount>> findById(@PathVariable("id")UUID id){
		return service.findById(id)
					.map(c -> ResponseEntity.ok()
											.contentType(MediaType.APPLICATION_JSON)
											.body(c))
					.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<BootCoinAccount>> update(@PathVariable("id") UUID id, @RequestBody BootCoinAccount entity){
		Mono<BootCoinAccount> monoBody = Mono.just(entity);
		Mono<BootCoinAccount> monoBD = service.findById(id);
		
		return monoBD
				.zipWith(monoBody, (bd, bca) ->{
					
					
					return bd;
				})
				.flatMap(service::update)
				.map(bca -> ResponseEntity.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(bca))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
				
	}
	@PostMapping
	public Mono<ResponseEntity<BootCoinAccount>> crear(@RequestBody BootCoinAccount body,final ServerHttpRequest rq){
		body.setId(UUID.randomUUID());
		return service.create(body)
				.map(bca -> ResponseEntity.created(URI.create(rq.getURI().toString().concat("/").concat(bca.getId().toString())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(bca)
							);
	}
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id")UUID id){
		return service.findById(id)
				.flatMap(bca -> service.deleteById(bca.getId())
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				)
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}

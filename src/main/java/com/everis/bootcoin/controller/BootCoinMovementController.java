package com.everis.bootcoin.controller;

import java.net.URI;
import java.time.LocalDateTime;
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

import com.everis.bootcoin.dto.BootCoinMovementDTO;
import com.everis.bootcoin.model.BootCoinMovement;
import com.everis.bootcoin.service.BootCoinMovementService;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("bootcoin-movement")
public class BootCoinMovementController {
	@Autowired
	private BootCoinMovementService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<BootCoinMovement>>> findAll(){
		Flux<BootCoinMovement> fluxBCMovement = service.findAll();
		return Mono.just(ResponseEntity
							.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(fluxBCMovement));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<BootCoinMovement>> findById(@PathVariable("id")UUID id){
		return service.findById(id)
					.map(c -> ResponseEntity.ok()
											.contentType(MediaType.APPLICATION_JSON)
											.body(c))
					.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<BootCoinMovement>> update(@PathVariable("id") UUID id, @RequestBody BootCoinMovement entity){
		Mono<BootCoinMovement> monoBody = Mono.just(entity);
		Mono<BootCoinMovement> monoBD = service.findById(id);
		
		return monoBD
				.zipWith(monoBody, (bd, bcm) ->{
					bd.setAmount(bcm.getAmount());
					bd.setCellphoneFrom(bcm.getCellphoneFrom());
					bd.setCellphoneTo(bcm.getCellphoneTo());
					bd.setRate(bcm.getRate());
					
					return bd;
				})
				.flatMap(service::update)
				.map(bcm -> ResponseEntity.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(bcm))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
				
	}
	@PostMapping
	public Mono<ResponseEntity<BootCoinMovement>> create(@RequestBody BootCoinMovement body,final ServerHttpRequest rq){
		body.setId(UUID.randomUUID());
		body.setDateMovement(LocalDateTime.now());
		return service.create(body)
				.map(bcm -> ResponseEntity.created(URI.create(rq.getURI().toString().concat("/").concat(bcm.getId().toString())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(bcm)
							);
	}
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id")UUID id){
		return service.findById(id)
				.flatMap(cr -> service.deleteById(cr.getId())
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				)
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping("/publish-offer")
	public Mono<ResponseEntity<BootCoinMovement>> publishOffer(@RequestBody BootCoinMovement body,final ServerHttpRequest rq){
		return service.publishOffer(body)
				.map(bcm -> ResponseEntity.created(URI.create(rq.getURI().toString().concat("/").concat(bcm.getId().toString())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(bcm)
							);
	}
	
	@GetMapping("/offers")
	public Mono<ResponseEntity<Flux<BootCoinMovement>>> findAllOffers(){
		Flux<BootCoinMovement> fluxBCMovement = service.findAllOffers();
		return Mono.just(ResponseEntity
							.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(fluxBCMovement));
	}
	
	@PutMapping("/sell/{id}")
	public Mono<ResponseEntity<BootCoinMovement>> sell(@PathVariable("id") UUID id, @RequestBody BootCoinMovementDTO body){
		return service.sell(id, body)
				.map(bcm -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(bcm))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
}

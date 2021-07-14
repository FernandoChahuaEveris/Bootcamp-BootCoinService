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

import com.everis.bootcoin.model.CurrencyRate;
import com.everis.bootcoin.service.CurrencyRateService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("currency-rate")
public class CurrencyRateController {
	@Autowired
	private CurrencyRateService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<CurrencyRate>>> findAll(){
		Flux<CurrencyRate> fluxCurrency = service.findAll();
		return Mono.just(ResponseEntity
							.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(fluxCurrency));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<CurrencyRate>> findById(@PathVariable("id")UUID id){
		return service.findById(id)
					.map(c -> ResponseEntity.ok()
											.contentType(MediaType.APPLICATION_JSON)
											.body(c))
					.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<CurrencyRate>> update(@PathVariable("id") UUID id, @RequestBody CurrencyRate entity){
		Mono<CurrencyRate> monoBody = Mono.just(entity);
		Mono<CurrencyRate> monoBD = service.findById(id);
		
		return monoBD
				.zipWith(monoBody, (bd, cr) ->{
					bd.setRate(cr.getRate());
					return bd;
				})
				.flatMap(service::update)
				.map(cr -> ResponseEntity.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(cr))
				.defaultIfEmpty(new ResponseEntity<CurrencyRate>(HttpStatus.NOT_FOUND));
				
	}
	
	@PostMapping
	public Mono<ResponseEntity<CurrencyRate>> create(@RequestBody CurrencyRate body,final ServerHttpRequest rq){
		body.setId(UUID.randomUUID());
		return service.create(body)
				.map(cr -> ResponseEntity.created(URI.create(rq.getURI().toString().concat("/").concat(cr.getId().toString())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(cr)
							);
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id")UUID id){
		return service.findById(id)
				.flatMap(cr -> {
					return service.deleteById(cr.getId())
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
				})
				.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
}

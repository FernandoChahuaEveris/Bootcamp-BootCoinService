package com.everis.bootcoin.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document
public class CurrencyRate {
	@Id
	@Builder.Default
	private UUID id = UUID.randomUUID();
	
	private String fromCurrency;
	private String toCurrency;
	
	private Double rate;
}

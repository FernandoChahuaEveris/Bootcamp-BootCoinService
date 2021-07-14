package com.everis.bootcoin.model;

import java.time.LocalDateTime;
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
public class BootCoinMovement {
	@Id
	@Builder.Default
	private UUID id = UUID.randomUUID();
	
	private String cellphoneFrom;
	private String cellphoneTo;
	
	private String typeMovement;
	
	private String state;
	private Double amount;
	private Double rate;
	
	@Builder.Default
	private LocalDateTime dateMovement = LocalDateTime.now();
}

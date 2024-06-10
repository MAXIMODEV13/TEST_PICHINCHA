package com.banco.springboot.app.tipo.cambio.models.service;
import com.banco.springboot.app.tipo.cambio.models.entity.Tipocambio;
import com.banco.springboot.app.tipo.cambio.models.dto.RequestCalculateDTO;
import com.banco.springboot.app.tipo.cambio.models.dto.ResponseCalculateDTO;
import com.banco.springboot.app.tipo.cambio.models.dto.TipocambioDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TipocambioServiceProccess {
	public Mono<List<TipocambioDTO>> listar();
	public Mono<TipocambioDTO> get(Long id);
	public Mono<TipocambioDTO> create(Tipocambio tipocambio, Long userId);
	public Mono<TipocambioDTO> update(Tipocambio tipocambio, Long id, Long userId);
	public Mono<ResponseCalculateDTO> calculate (RequestCalculateDTO requestCalculate);

}

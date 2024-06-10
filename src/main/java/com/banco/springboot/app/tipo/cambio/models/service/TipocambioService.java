package com.banco.springboot.app.tipo.cambio.models.service;

import com.banco.springboot.app.tipo.cambio.models.entity.Usuario;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TipocambioService {
	public Mono<Usuario> findById(Long id);

}

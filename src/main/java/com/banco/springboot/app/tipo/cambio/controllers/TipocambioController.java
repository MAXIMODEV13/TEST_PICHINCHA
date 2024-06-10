package com.banco.springboot.app.tipo.cambio.controllers;

import com.banco.springboot.app.tipo.cambio.models.entity.Usuario;
import com.banco.springboot.app.tipo.cambio.models.dao.TipocambioDao;
import com.banco.springboot.app.tipo.cambio.models.dao.UsuarioDao;
import com.banco.springboot.app.tipo.cambio.models.dto.TipocambioDTO;
import com.banco.springboot.app.tipo.cambio.models.entity.Tipocambio;
import com.banco.springboot.app.tipo.cambio.models.service.TipocambioService;
import com.banco.springboot.app.tipo.cambio.models.service.TipocambioServiceProccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RefreshScope
@RestController
@RequestMapping("/crud")
public class TipocambioController {

	private static Logger log = LoggerFactory.getLogger(TipocambioController.class);

	@Autowired
	private TipocambioServiceProccess tipocambioServiceProccess;

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ResponseEntity<TipocambioDTO>> crear(@RequestBody Tipocambio tipocambio,
													 @RequestHeader("id_user") String userId) {
		Long userIdLong = Long.valueOf(userId);
		return tipocambioServiceProccess.create(tipocambio, userIdLong)
				.map(tipocambioDTO -> ResponseEntity.status(HttpStatus.CREATED).body(tipocambioDTO));
	}


	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ResponseEntity<TipocambioDTO>> editar(@RequestBody Tipocambio tipocambio,
								@PathVariable Long id,
								@RequestHeader("id_user") String userId) {
		Long userIdLong = Long.valueOf(userId);
		return tipocambioServiceProccess.update(tipocambio, id, userIdLong)
				.map(tipocambioDTO -> ResponseEntity.status(HttpStatus.CREATED).body(tipocambioDTO));
	}


	@GetMapping("/listar")
	public Mono<ResponseEntity<List<TipocambioDTO>>> listar() {
		return tipocambioServiceProccess.listar()
				.flatMap(lista -> Mono.just(ResponseEntity.ok(lista)))
				.onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Collections.emptyList())));
	}
	

}

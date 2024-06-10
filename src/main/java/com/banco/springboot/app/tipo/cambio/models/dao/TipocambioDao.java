package com.banco.springboot.app.tipo.cambio.models.dao;

import com.banco.springboot.app.tipo.cambio.models.entity.Tipocambio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TipocambioDao extends ReactiveCrudRepository<Tipocambio, Long> {
    @Query("select t from Tipocambio t where t.monedaOrigen=?1 and t.monedaDestino=?2")
    public Mono<Tipocambio> obtenerPorMonedaOrigenMonedaDestino(@Param("monedaOrigen") String monedaOrigen, @Param("monedaDestino") String monedaDestino);
}

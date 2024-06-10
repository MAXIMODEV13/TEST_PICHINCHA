package com.banco.springboot.app.tipo.cambio.models.dao;

import com.banco.springboot.app.tipo.cambio.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UsuarioDao extends ReactiveCrudRepository<Usuario, Long> {
    public Optional<Usuario> findByUsername(String username);
}

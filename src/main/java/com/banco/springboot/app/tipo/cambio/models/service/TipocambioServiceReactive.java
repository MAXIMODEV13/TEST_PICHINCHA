package com.banco.springboot.app.tipo.cambio.models.service;
import com.banco.springboot.app.tipo.cambio.models.dao.UsuarioDao;
import com.banco.springboot.app.tipo.cambio.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TipocambioServiceReactive implements TipocambioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public Mono<Usuario> findById(Long id) {
        return usuarioDao.findById(id);
    }
}


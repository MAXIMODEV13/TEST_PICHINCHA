package com.banco.springboot.app.tipo.cambio.models.impl;

import com.banco.springboot.app.tipo.cambio.infra.exceptions.TipoCambioException;
import com.banco.springboot.app.tipo.cambio.models.dao.TipocambioDao;
import com.banco.springboot.app.tipo.cambio.models.dao.UsuarioDao;
import com.banco.springboot.app.tipo.cambio.models.dto.RequestCalculateDTO;
import com.banco.springboot.app.tipo.cambio.models.dto.ResponseCalculateDTO;
import com.banco.springboot.app.tipo.cambio.models.dto.TipocambioDTO;
import com.banco.springboot.app.tipo.cambio.models.entity.Tipocambio;
import com.banco.springboot.app.tipo.cambio.models.entity.Usuario;
import com.banco.springboot.app.tipo.cambio.models.service.TipocambioServiceProccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TipocambioServiceProccessImpl implements TipocambioServiceProccess {

    @Autowired
    private UsuarioDao usuarioDao;
    
    @Autowired
    private TipocambioDao tipocambioDao;

    @Override
    public Mono<List<TipocambioDTO>> listar() {
        return tipocambioDao.findAll()
                .flatMap(tipoCambio -> usuarioDao.findById(tipoCambio.getCreateUser())
                        .map(usuario -> {
                            TipocambioDTO tipeNew = new TipocambioDTO();
                            tipeNew.setId(tipoCambio.getId());
                            tipeNew.setMonedaOrigen(tipoCambio.getMonedaOrigen());
                            tipeNew.setMonedaDestino(tipoCambio.getMonedaDestino());
                            tipeNew.setNombreOrigen(tipoCambio.getNombreOrigen());
                            tipeNew.setNombreDestino(tipoCambio.getNombreDestino());
                            tipeNew.setPrecio(tipoCambio.getPrecio());
                            tipeNew.setCreateAt(tipoCambio.getCreateAt().toString());
                            usuario.setPassword(null);
                            usuario.setUsername(null);
                            tipeNew.setCreateUser(usuario);
                            return tipeNew;
                        }))
                .collectList();
    }


    @Override
    public Mono<TipocambioDTO> get(Long id) {
        return tipocambioDao.findById(id)
                .switchIfEmpty(Mono.error(new TipoCambioException("Tipo de cambio con ID " + id + " no encontrado")))
                .flatMap(tipoCambio -> usuarioDao.findById(tipoCambio.getCreateUser())
                        .map(usuario -> {
                            TipocambioDTO tipeNew = new TipocambioDTO();
                            tipeNew.setId(tipoCambio.getId());
                            tipeNew.setMonedaOrigen(tipoCambio.getMonedaOrigen());
                            tipeNew.setMonedaDestino(tipoCambio.getMonedaDestino());
                            tipeNew.setNombreOrigen(tipoCambio.getNombreOrigen());
                            tipeNew.setNombreDestino(tipoCambio.getNombreDestino());
                            tipeNew.setPrecio(tipoCambio.getPrecio());
                            tipeNew.setCreateAt(tipoCambio.getCreateAt().toString());
                            usuario.setPassword(null);
                            usuario.setUsername(null);
                            tipeNew.setCreateUser(usuario);
                            return tipeNew;
                        })
                );
    }

    @Override
    public Mono<TipocambioDTO> create(Tipocambio tipocambio, Long userId) {
        tipocambio.setCreateUser(userId);
        return tipocambioDao.save(tipocambio)
                .flatMap(savedTipocambio -> get(savedTipocambio.getId()));
    }

    @Override
    public Mono<TipocambioDTO> update(Tipocambio tipocambio, Long id, Long userId) {
        return tipocambioDao.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Tipo de cambio no encontrado con ID: " + id)))
                .flatMap(existingTipocambio -> {
                    existingTipocambio.setNombreOrigen(tipocambio.getNombreOrigen());
                    existingTipocambio.setNombreDestino(tipocambio.getNombreDestino());
                    existingTipocambio.setPrecio(tipocambio.getPrecio());
                    existingTipocambio.setMonedaOrigen(tipocambio.getMonedaOrigen());
                    existingTipocambio.setMonedaDestino(tipocambio.getMonedaDestino());
                    return tipocambioDao.save(existingTipocambio);
                })
                .flatMap(updatedTipocambio -> get(updatedTipocambio.getId()));
    }

    @Override
    public Mono<ResponseCalculateDTO> calculate(RequestCalculateDTO requestCalculate) {
        return tipocambioDao
                .obtenerPorMonedaOrigenMonedaDestino(requestCalculate.getMonedaOrigen(), requestCalculate.getMonedaDestino())
                .map(tipocambio -> {
                    Double total = tipocambio.getPrecio() * requestCalculate.getMonto();
                    ResponseCalculateDTO responseCalculateDTO = new ResponseCalculateDTO();
                    responseCalculateDTO.setMonedaDestino(tipocambio.getMonedaDestino());
                    responseCalculateDTO.setMonedaOrigen(tipocambio.getMonedaOrigen());
                    responseCalculateDTO.setNombreOrigen(tipocambio.getNombreOrigen());
                    responseCalculateDTO.setNombreDestino(tipocambio.getNombreDestino());
                    responseCalculateDTO.setMonto(requestCalculate.getMonto());
                    responseCalculateDTO.setTotal(total);
                    return responseCalculateDTO;
                })
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Tipo de cambio no registrado")));
    }




}

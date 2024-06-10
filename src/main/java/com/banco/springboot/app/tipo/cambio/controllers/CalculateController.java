package com.banco.springboot.app.tipo.cambio.controllers;

import com.banco.springboot.app.tipo.cambio.models.dao.TipocambioDao;
import com.banco.springboot.app.tipo.cambio.models.dao.UsuarioDao;
import com.banco.springboot.app.tipo.cambio.models.dto.RequestCalculateDTO;
import com.banco.springboot.app.tipo.cambio.models.dto.ResponseCalculateDTO;
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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RefreshScope
@RestController
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequestMapping("/calculate")
public class CalculateController {

    private static Logger log = LoggerFactory.getLogger(TipocambioController.class);

    @Autowired
    private TipocambioServiceProccess tipocambioServiceProccess;

    @PostMapping("/calculate")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseCalculateDTO> calculate(@RequestBody RequestCalculateDTO requestCalculate,
                                                @RequestHeader("id_user") String userId) {
        return tipocambioServiceProccess.calculate(requestCalculate);
    }
}


package com.banco.springboot.app.tipo.cambio.infra.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UsuarioNoActivoException extends AuthenticationException {
    public UsuarioNoActivoException(String s) {
        super(s);
    }

    public UsuarioNoActivoException(String s, Throwable cause) {
        super(s, cause);
    }
}

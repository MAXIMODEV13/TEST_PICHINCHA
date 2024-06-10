package com.banco.springboot.app.tipo.cambio.infra.exceptions;

public class TipoCambioException extends RuntimeException{
    public TipoCambioException(String msg) {
        super(msg);
    }
    public TipoCambioException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

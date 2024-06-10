package com.banco.springboot.app.tipo.cambio.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.banco.springboot.app.tipo.cambio.models.entity.Usuario;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    // Llave secreta
    private final static String SECRET_KEY = "123456";
    // Tiempo de expiracion en segundos
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 259_200L;
    public String generarTokenEmp(Usuario user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer("tipo_cambio")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withClaim("nombre", user.getNombre())
                    .withClaim("apellido", user.getApellido())
                    .withClaim("email", user.getEmail())
                    .withClaim("username", user.getUsername())
                    .withClaim("role", user.getRoles().stream().findFirst().get().getNombre())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Ocurrio un error");
        }

        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            verifier = JWT.require(algorithm)
                    .withIssuer("tipo_cambio")
                    .build()
                    .verify(token);

            verifier.getSubject();
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }

        assert verifier != null;
        if (verifier.getSubject() == null) {
            throw new RuntimeException("El usuario es invalido");
        }

        return verifier.getSubject();
    }

    private Instant generarFechaExpiracion() {
        long exp = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        return LocalDateTime.now().plus(Duration.ofMillis(exp)).toInstant(ZoneOffset.of("-05:00"));
    }
}

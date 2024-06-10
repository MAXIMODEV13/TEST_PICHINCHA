package com.pichincha.springboot.app.tipo.cambio.models.impl;

import com.banco.springboot.app.tipo.cambio.infra.exceptions.TipoCambioException;
import com.banco.springboot.app.tipo.cambio.models.dao.TipocambioDao;
import com.banco.springboot.app.tipo.cambio.models.dao.UsuarioDao;
import com.banco.springboot.app.tipo.cambio.models.dto.RequestCalculateDTO;
import com.banco.springboot.app.tipo.cambio.models.dto.ResponseCalculateDTO;
import com.banco.springboot.app.tipo.cambio.models.dto.TipocambioDTO;
import com.banco.springboot.app.tipo.cambio.models.entity.Tipocambio;
import com.banco.springboot.app.tipo.cambio.models.entity.Usuario;
import com.banco.springboot.app.tipo.cambio.models.entity.Role;
import com.banco.springboot.app.tipo.cambio.models.impl.TipocambioServiceProccessImpl;
import com.banco.springboot.app.tipo.cambio.models.service.TipocambioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TipocambioDaoImplTest {
    @Mock
    private TipocambioDao tipocambioDao;

    @Mock
    private TipocambioService itemService;
    @Mock
    private UsuarioDao usuarioDao;
    @InjectMocks
    private TipocambioServiceProccessImpl tipocambioDaoImpl;


    @Test
    public void testListar() {
        // Datos de prueba
        Tipocambio tipoCambio1 = new Tipocambio();
        tipoCambio1.setId(1L);
        tipoCambio1.setMonedaOrigen("USD");
        tipoCambio1.setMonedaDestino("EUR");
        tipoCambio1.setNombreOrigen("Dólar estadounidense");
        tipoCambio1.setNombreDestino("Euro");
        tipoCambio1.setPrecio(0.84);
        tipoCambio1.setCreateAt(new Date());
        tipoCambio1.setCreateUser(1L);

        Tipocambio tipoCambio2 = new Tipocambio();
        tipoCambio2.setId(2L);
        tipoCambio2.setMonedaOrigen("EUR");
        tipoCambio2.setMonedaDestino("USD");
        tipoCambio2.setNombreOrigen("Euro");
        tipoCambio2.setNombreDestino("Dólar estadounidense");
        tipoCambio2.setPrecio(1.19);
        tipoCambio2.setCreateAt(new Date());
        tipoCambio2.setCreateUser(1L);

        List<Tipocambio> listaTiposCambio = new ArrayList<>();
        listaTiposCambio.add(tipoCambio1);
        listaTiposCambio.add(tipoCambio2);

        // Mockear el comportamiento del tipocambioDao
        when(tipocambioDao.findAll()).thenReturn(Flux.fromIterable(listaTiposCambio));

        // Mockear el comportamiento del usuarioDao
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setUsername("usuario1");
        usuarioMock.setPassword("password1");
        usuarioMock.setNombre("Ricardo");
        usuarioMock.setApellido("Castillo");
        usuarioMock.setEmail("ricardo@mail.com");
        usuarioMock.setEnabled(true);
        Role role = new Role();
        role.setId(1L);
        role.setNombre("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        usuarioMock.setRoles(roles);

        when(usuarioDao.findById(anyLong())).thenReturn(Mono.just(usuarioMock));

        // Ejecutar el método que queremos probar
        Mono<List<TipocambioDTO>> resultadoMono = tipocambioDaoImpl.listar();

        // Verificar que el método devuelve el resultado esperado
        StepVerifier.create(resultadoMono)
                .consumeNextWith(lista -> {
                    assertThat(lista.size()).isEqualTo(2);
                    assertThat(lista.get(0).getId()).isEqualTo(1L);
                    assertThat(lista.get(0).getMonedaOrigen()).isEqualTo("USD");
                    assertThat(lista.get(0).getMonedaDestino()).isEqualTo("EUR");
                    assertThat(lista.get(0).getNombreOrigen()).isEqualTo("Dólar estadounidense");
                    assertThat(lista.get(0).getNombreDestino()).isEqualTo("Euro");
                    assertThat(lista.get(0).getPrecio()).isEqualTo(0.84);
                    assertThat(lista.get(0).getCreateUser().getEmail()).isEqualTo("ricardo@mail.com");
                    assertThat(lista.get(1).getId()).isEqualTo(2L);
                    assertThat(lista.get(1).getMonedaOrigen()).isEqualTo("EUR");
                    assertThat(lista.get(1).getMonedaDestino()).isEqualTo("USD");
                    assertThat(lista.get(1).getNombreOrigen()).isEqualTo("Euro");
                    assertThat(lista.get(1).getNombreDestino()).isEqualTo("Dólar estadounidense");
                    assertThat(lista.get(1).getPrecio()).isEqualTo(1.19);
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testGet() {
        // Datos de prueba
        Long id = 1L;
        Tipocambio tipoCambio = new Tipocambio();
        tipoCambio.setId(id);
        tipoCambio.setMonedaOrigen("USD");
        tipoCambio.setMonedaDestino("EUR");
        tipoCambio.setNombreOrigen("Dólar estadounidense");
        tipoCambio.setNombreDestino("Euro");
        tipoCambio.setPrecio(0.84);
        tipoCambio.setCreateAt(new Date());
        tipoCambio.setCreateUser(1L);

        // Mockear el comportamiento del tipocambioDao
        when(tipocambioDao.findById(id)).thenReturn(Mono.just(tipoCambio));

        // Mockear el comportamiento del usuarioDao
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setUsername("usuario1");
        usuarioMock.setPassword("password1");
        usuarioMock.setNombre("Ricardo");
        usuarioMock.setApellido("Castillo");
        usuarioMock.setEmail("ricardo@mail.com");
        usuarioMock.setEnabled(true);

        Role role = new Role();
        role.setId(1L);
        role.setNombre("ADMIN");

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        usuarioMock.setRoles(roles);

        when(usuarioDao.findById(anyLong())).thenReturn(Mono.just(usuarioMock));

        // Ejecutar el método que queremos probar
        Mono<TipocambioDTO> resultadoMono = tipocambioDaoImpl.get(id);

        // Verificar que el método devuelve el resultado esperado
        StepVerifier.create(resultadoMono)
                .consumeNextWith(tipocambioDTO -> {
                    assertThat(tipocambioDTO.getId()).isEqualTo(id);
                    assertThat(tipocambioDTO.getMonedaOrigen()).isEqualTo("USD");
                    assertThat(tipocambioDTO.getMonedaDestino()).isEqualTo("EUR");
                    assertThat(tipocambioDTO.getNombreOrigen()).isEqualTo("Dólar estadounidense");
                    assertThat(tipocambioDTO.getNombreDestino()).isEqualTo("Euro");
                    assertThat(tipocambioDTO.getPrecio()).isEqualTo(0.84);
                    assertThat(tipocambioDTO.getCreateUser().getEmail()).isEqualTo("ricardo@mail.com");
                })
                .expectComplete()
                .verify();
    }
    @Test
    public void testGetNoEncontrado() {
        // Datos de prueba
        Long id = 1L;

        // Mockear el comportamiento del tipocambioDao
        when(tipocambioDao.findById(id)).thenReturn(Mono.empty());

        // Ejecutar el método que queremos probar
        Mono<TipocambioDTO> resultadoMono = tipocambioDaoImpl.get(id);

        // Verificar que el método devuelve un error cuando no encuentra el tipo de cambio
        StepVerifier.create(resultadoMono)
                .expectErrorMatches(throwable -> throwable instanceof TipoCambioException &&
                        throwable.getMessage().equals("Tipo de cambio con ID " + id + " no encontrado"))
                .verify();
    }
    @Test
    public void testCalculate() {
        // Datos de prueba
        RequestCalculateDTO requestCalculate = new RequestCalculateDTO();
        requestCalculate.setMonedaOrigen("USD");
        requestCalculate.setMonedaDestino("EUR");
        requestCalculate.setMonto(84.0);

        Tipocambio tipoCambio = new Tipocambio();
        tipoCambio.setId(1L);
        tipoCambio.setMonedaOrigen("USD");
        tipoCambio.setMonedaDestino("EUR");
        tipoCambio.setNombreOrigen("Dólar estadounidense");
        tipoCambio.setNombreDestino("Euro");
        tipoCambio.setPrecio(0.84);
        tipoCambio.setCreateAt(new Date());
        tipoCambio.setCreateUser(1L);

        // Mockear el comportamiento del tipocambioDao
        when(tipocambioDao
                .obtenerPorMonedaOrigenMonedaDestino(
                        requestCalculate.getMonedaOrigen(), requestCalculate.getMonedaDestino()))
                .thenReturn(Mono.just(tipoCambio));

        // Ejecutar el método que queremos probar
        Mono<ResponseCalculateDTO> resultado = tipocambioDaoImpl.calculate(requestCalculate);

        // Verificar que el método devuelve el resultado esperado
        StepVerifier.create(resultado)
                .assertNext(response -> Assertions.assertEquals(70.56, response.getTotal()))
                .verifyComplete();
    }

    @Test
    public void testUpdate() {
        // Datos de prueba
        Long userId = 1L;
        Long id = 1L;
        Tipocambio tipocambio = new Tipocambio();
        tipocambio.setMonedaOrigen("USD");
        tipocambio.setMonedaDestino("EUR");
        tipocambio.setNombreOrigen("Dólar estadounidense");
        tipocambio.setNombreDestino("Euro");
        tipocambio.setPrecio(0.84);
        tipocambio.setCreateAt(new Date());

        Tipocambio existingTipocambio = new Tipocambio();
        existingTipocambio.setId(id);
        existingTipocambio.setMonedaOrigen("EUR");
        existingTipocambio.setMonedaDestino("USD");
        existingTipocambio.setNombreOrigen("Euro");
        existingTipocambio.setNombreDestino("Dólar estadounidense");
        existingTipocambio.setPrecio(1.19);
        existingTipocambio.setCreateAt(new Date());
        existingTipocambio.setCreateUser(userId);

        Tipocambio updatedTipocambio = new Tipocambio();
        updatedTipocambio.setId(id);
        updatedTipocambio.setMonedaOrigen("USD");
        updatedTipocambio.setMonedaDestino("EUR");
        updatedTipocambio.setNombreOrigen("Dólar estadounidense");
        updatedTipocambio.setNombreDestino("Euro");
        updatedTipocambio.setPrecio(0.84);
        updatedTipocambio.setCreateAt(new Date());
        updatedTipocambio.setCreateUser(userId);

        // Mockear el comportamiento del tipocambioDao
        when(tipocambioDao.findById(id)).thenReturn(Mono.just(existingTipocambio));
        when(tipocambioDao.save(any(Tipocambio.class))).thenReturn(Mono.just(updatedTipocambio));
        when(tipocambioDao.findById(updatedTipocambio.getId())).thenReturn(Mono.just(updatedTipocambio));
        when(usuarioDao.findById(anyLong())).thenReturn(Mono.just(createMockUsuario()));

        // Ejecutar el método que queremos probar
        Mono<TipocambioDTO> resultadoMono = tipocambioDaoImpl.update(tipocambio, id, userId);

        // Verificar que el método devuelve el resultado esperado
        StepVerifier.create(resultadoMono)
                .consumeNextWith(tipocambioDTO -> {
                    assertThat(tipocambioDTO.getId()).isEqualTo(updatedTipocambio.getId());
                    assertThat(tipocambioDTO.getMonedaOrigen()).isEqualTo("USD");
                    assertThat(tipocambioDTO.getMonedaDestino()).isEqualTo("EUR");
                    assertThat(tipocambioDTO.getNombreOrigen()).isEqualTo("Dólar estadounidense");
                    assertThat(tipocambioDTO.getNombreDestino()).isEqualTo("Euro");
                    assertThat(tipocambioDTO.getPrecio()).isEqualTo(0.84);
                    assertThat(tipocambioDTO.getCreateUser().getEmail()).isEqualTo("ricardo@mail.com");
                })
                .expectComplete()
                .verify();
    }
    @Test
    public void testCreate() {
        // Datos de prueba
        Long userId = 1L;
        Tipocambio tipocambio = new Tipocambio();
        tipocambio.setMonedaOrigen("USD");
        tipocambio.setMonedaDestino("EUR");
        tipocambio.setNombreOrigen("Dólar estadounidense");
        tipocambio.setNombreDestino("Euro");
        tipocambio.setPrecio(0.84);
        tipocambio.setCreateAt(new Date());

        Tipocambio savedTipocambio = new Tipocambio();
        savedTipocambio.setId(1L);
        savedTipocambio.setMonedaOrigen("USD");
        savedTipocambio.setMonedaDestino("EUR");
        savedTipocambio.setNombreOrigen("Dólar estadounidense");
        savedTipocambio.setNombreDestino("Euro");
        savedTipocambio.setPrecio(0.84);
        savedTipocambio.setCreateAt(new Date());
        savedTipocambio.setCreateUser(userId);

        // Mockear el comportamiento del tipocambioDao
        when(tipocambioDao.save(any(Tipocambio.class))).thenReturn(Mono.just(savedTipocambio));
        when(tipocambioDao.findById(savedTipocambio.getId())).thenReturn(Mono.just(savedTipocambio));
        when(usuarioDao.findById(anyLong())).thenReturn(Mono.just(createMockUsuario()));

        // Ejecutar el método que queremos probar
        Mono<TipocambioDTO> resultadoMono = tipocambioDaoImpl.create(tipocambio, userId);

        // Verificar que el método devuelve el resultado esperado
        StepVerifier.create(resultadoMono)
                .consumeNextWith(tipocambioDTO -> {
                    assertThat(tipocambioDTO.getId()).isEqualTo(savedTipocambio.getId());
                    assertThat(tipocambioDTO.getMonedaOrigen()).isEqualTo("USD");
                    assertThat(tipocambioDTO.getMonedaDestino()).isEqualTo("EUR");
                    assertThat(tipocambioDTO.getNombreOrigen()).isEqualTo("Dólar estadounidense");
                    assertThat(tipocambioDTO.getNombreDestino()).isEqualTo("Euro");
                    assertThat(tipocambioDTO.getPrecio()).isEqualTo(0.84);
                    assertThat(tipocambioDTO.getCreateUser().getEmail()).isEqualTo("ricardo@mail.com");
                })
                .expectComplete()
                .verify();
    }
    private Usuario createMockUsuario() {
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setUsername("usuario1");
        usuarioMock.setPassword("password1");
        usuarioMock.setNombre("Ricardo");
        usuarioMock.setApellido("Castillo");
        usuarioMock.setEmail("ricardo@mail.com");
        usuarioMock.setEnabled(true);
        Role role = new Role();
        role.setId(1L);
        role.setNombre("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        usuarioMock.setRoles(roles);
        return usuarioMock;
    }
}


package com.banco.springboot.app.tipo.cambio.infra.security;

import com.banco.springboot.app.tipo.cambio.models.dao.UsuarioDao;
import com.banco.springboot.app.tipo.cambio.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con el nombre " + username + " no existe"));
        return new UserDetailsImpl(user);
    }
}

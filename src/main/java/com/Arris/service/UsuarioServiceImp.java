package com.Arris.service;


import com.Arris.controllers.dto.UsuarioRegistroDTO;
import com.Arris.models.DetallePedido;
import com.Arris.models.Rol;
import com.Arris.models.Usuario;
import com.Arris.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImp implements UsuarioService {


   @Autowired
   UsuarioRepository usuarioRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Usuario guardarUsuario(UsuarioRegistroDTO registroDTO) {
        Usuario usuario = new Usuario(registroDTO.getIdUsuario(),registroDTO.getNombre(),registroDTO.getTelefono(),registroDTO.getEmail(),registroDTO.getDireccion(),passwordEncoder.encode(registroDTO.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username);
        if (usuario == null){
            throw new UsernameNotFoundException("Usuario o password invalidos");
        }
        return new User(usuario.getEmail(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombreRol())).collect(Collectors.toList());
    }
}

package com.Arris.service;


import com.Arris.controllers.dto.UsuarioRegistroDTO;
import com.Arris.models.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsuarioService extends UserDetailsService {

    public Usuario guardarUsuario(UsuarioRegistroDTO registroDTO);

    public List<Usuario> listarUsuarios();
}

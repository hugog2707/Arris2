package com.Arris.controllers;


import com.Arris.controllers.dto.UsuarioRegistroDTO;
import com.Arris.models.Envio;
import com.Arris.models.Rol;
import com.Arris.models.RolUsuario;
import com.Arris.models.Usuario;
import com.Arris.repository.UsuarioRepository;
import com.Arris.service.EnvioService;
import com.Arris.service.RolUsuarioService;
import com.Arris.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/registro")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EnvioService envioService;

    @Autowired
    RolUsuarioService rolUsuarioService;

    @ModelAttribute("usuario")
    public UsuarioRegistroDTO retornarNuevoUsuarioRegistroDTO() {
        return new UsuarioRegistroDTO();
    }

    @GetMapping("/editar_datos_cliente")
    public String editarDatosCliente(Model model){
        List<Usuario> usuario = usuarioService.listarUsuarios();
        List<Envio> envio = envioService.getAll();
        List<RolUsuario> rol = rolUsuarioService.getAll();
        model.addAttribute("rol",rol);
        model.addAttribute("envio",envio);
        model.addAttribute("usuario",usuario);
        return "interfaz_cliente/templates/editar";
    }

    @GetMapping
    public String mostrarFormularioDeRegistro() {
        return "registro";
    }

    @PostMapping
    public String registrarCuentaDeUsuario(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO) {
        usuarioService.guardarUsuario(registroDTO);
        return "redirect:/registro?exito";
    }

    @PostMapping("/guardar_usuario_admin")
    public String registrarCuentaDeUsuarioadmin(UsuarioRegistroDTO registroDTO, RedirectAttributes redirectAttrs) {
        usuarioService.guardarUsuario(registroDTO);
        redirectAttrs
                .addFlashAttribute("mensaje", "Se Registro el Nuevo Usuario Satisfactoriamente, Nombre del Usuario: " + registroDTO.getNombre() +", Correo Electronico: " + registroDTO.getEmail() + ", Recuerda que se Envio un Mensaje a Este Correo con La Contraseña ✔")
                .addFlashAttribute("clase", "success");
        return "redirect:/gestion_ventas_admin";
    }

    @PostMapping("/editar_datos_cliente_env")
    public String editarDatosClienteEnv(@ModelAttribute("usuario") UsuarioRegistroDTO registroDTO, RedirectAttributes redirectAttrs){
        registroDTO.setIdUsuario(registroDTO.getIdUsuario());
        registroDTO.setDireccion(registroDTO.getDireccion());
        registroDTO.setEmail(registroDTO.getEmail());
        registroDTO.setNombre(registroDTO.getNombre());
        registroDTO.setTelefono(registroDTO.getTelefono());
        registroDTO.setPassword(registroDTO.getPassword());
        redirectAttrs
                .addFlashAttribute("mensaje", "Se A Guardado Satisfactioriamente Su Información" + registroDTO.getTelefono() +  " ✔")
                .addFlashAttribute("clase", "success");
        System.out.println("Datos Actualizados");
        return "redirect:/registro/editar_datos_cliente";
    }


}

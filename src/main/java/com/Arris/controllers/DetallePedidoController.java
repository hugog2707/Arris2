package com.Arris.controllers;

import com.Arris.models.*;
import com.Arris.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class DetallePedidoController {

    @Autowired
    DetallePedidoService detallePedidoService;

    @Autowired
    RolUsuarioService rolUsuarioService;

    @Autowired
    PedidoService pedidoService;

    @Autowired
    ProductoService productoService;

    @Autowired
    EnvioService envioService;

    @Autowired
    VentaService ventaService;

    @GetMapping("/alla")
    public ArrayList<DetallePedido> getAllDetallePedidos(){
        return detallePedidoService.getAll();
    }

    @GetMapping("/inicio")
    public String inicioPagina(){
        return "/web/index";
    }

    @GetMapping("/gestion_ventas_admin")
    public String listarAdmin(Model model){
        List<DetallePedido> detallePedido = detallePedidoService.getAll();
        List<RolUsuario> rolUsuario = rolUsuarioService.getAll();
        List<Pedido> pedido = pedidoService.getAll();
        List<Producto> producto = productoService.getAll();
        List<Venta> venta = ventaService.getAll();
        model.addAttribute("pedido", detallePedido);
        model.addAttribute("rolUsuario", rolUsuario);
        model.addAttribute("mostrarPedidos", pedido);
        model.addAttribute("producto", producto);
        model.addAttribute("venta", venta);
        return "interfaz_administrador/templates/gestion_ventas";
    }

    @GetMapping("/gestion_ventas_empleado")
    public String listarEmpleado(Model model){
        List<DetallePedido> detallePedido = detallePedidoService.getAll();
        List<RolUsuario> rolUsuario = rolUsuarioService.getAll();
        List<Pedido> pedido = pedidoService.getAll();
        List<Producto> producto = productoService.getAll();
        model.addAttribute("pedido", detallePedido);
        model.addAttribute("rolUsuario", rolUsuario);
        model.addAttribute("mostrarPedidos", pedido);
        model.addAttribute("producto", producto);
        return "interfaz_empleado/templates/ventas";
    }

    @GetMapping("/compras_cliente")
    public String listarComprasCliente(Model model, Usuario usuario){
        List<DetallePedido> detallePedido = detallePedidoService.getAll();
        List<RolUsuario> rolUsuario = rolUsuarioService.getAll();
        List<Pedido> pedido = pedidoService.getAll();
        List<Producto> producto = productoService.getAll();
        List<Envio> envio = envioService.getAll();
        List<Venta> venta = ventaService.getAll();
        model.addAttribute("pedido", detallePedido);
        model.addAttribute("rolUsuario", rolUsuario);
        model.addAttribute("mostrarPedidos", pedido);
        model.addAttribute("producto", producto);
        model.addAttribute("envio", envio);
        model.addAttribute("venta", venta);
        return "interfaz_cliente/templates/compras";
    }

    @GetMapping("/finda/{id}")
    public Optional<DetallePedido> getDetallePedido(@PathVariable("id") long idDetallePedido){
        return detallePedidoService.getById(idDetallePedido);
    }

    @PostMapping("/guardar_nueva_venta_admin")
    public String GuardarVenta(DetallePedido detallePedido, RedirectAttributes redirectAttrs, Usuario usuario) {
        detallePedidoService.save(detallePedido);
        redirectAttrs
                .addFlashAttribute("mensaje", "Se A Guardado Satisfactioriamente La Nueva Venta, Numero De Venta#: " + detallePedido.getIdDetallePedido() + ", Nombre Del Cliente: " + detallePedido.getPedido().getCliente().getNombre() + ", Nombre Del Producto: " + detallePedido.getProducto().getNombre() + ", Cantidad: " + detallePedido.getCantidad() +  ", Estado: " + detallePedido.getEstado() +  " ✔")
                .addFlashAttribute("clase", "success");
        System.out.println("pedido guardado con exito");
        return "redirect:/gestion_ventas_admin";
    }

    @PostMapping("/guardar_nueva_venta_empleado")
    public String GuardarVentaEmpleado(DetallePedido detallePedido, RedirectAttributes redirectAttrs, Usuario usuario) {
        detallePedidoService.save(detallePedido);
        redirectAttrs
                .addFlashAttribute("mensaje", "Se A Guardado Satisfactioriamente La Nueva Venta, Numero De Venta#: " + detallePedido.getIdDetallePedido() + ", Nombre Del Cliente: " + detallePedido.getPedido().getCliente().getNombre() + ", Nombre Del Producto: " + detallePedido.getProducto().getNombre() + ", Cantidad: " + detallePedido.getCantidad() +  ", Estado: " + detallePedido.getEstado() +  " ✔")
                .addFlashAttribute("clase", "success");
        System.out.println("pedido guardado con exito");
        return "redirect:/gestion_ventas_empleado";
    }
    // Espedifica el nombre de una vista específica que se usará para mostrar el error
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String databaseError(RedirectAttributes redirectAttrs) {
        // Nada que hacer.
        // Devuelve el nombre lógico de la vista de la página de error,
        // el cual se pasa el view-resolver del modo habitual.
        // La excepción NO está disponible en esta vista
        // (no se añade al modelo) pero puedes revisar
        // "Extendiendo el ExceptionHandlerExceptionResolver"
        // más abajo.
        redirectAttrs
                .addFlashAttribute("mensaje", "Productos no disponibles en stock, comuniquese con su administrador")
                .addFlashAttribute("clase", "danger");
        return "redirect:/gestion_ventas_empleado";
    }

    @PostMapping("/cambiar_estado_venta")
    public String CambiarEstado(DetallePedido detallePedido, RedirectAttributes redirectAttrs){
        detallePedidoService.save(detallePedido);
        redirectAttrs
                .addFlashAttribute("mensaje", "El Estado De La Venta #" + detallePedido.getIdDetallePedido() +" A Sido Editada Satisfactoriamente Por: " + detallePedido.getEstado() + " ✔")
                .addFlashAttribute("clase", "success");
        System.out.println("estado actualizado con exito");
        return "redirect:/gestion_ventas_admin";
    }

    @PostMapping("/savea")
    public DetallePedido saveDetallePedido(@RequestBody DetallePedido detallePedido){
        return detallePedidoService.save(detallePedido);
    }

    @DeleteMapping("/deletea/{id}")
    public String deleteDetallePedidoById(@PathVariable("id") long idDetallePedido){
        if (detallePedidoService.deleteById(idDetallePedido))
            return "se ha eliminado";
        else
            return "no se elimino";
    }


}

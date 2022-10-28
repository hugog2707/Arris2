package com.Arris.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter @Column(name = "id_usuario")
    private long idUsuario;
    @Setter @Getter @Column(name = "nombre")
    private String nombre;
    @Setter @Getter @Column(name = "telefono")
    private String telefono;
    @Setter @Getter @Column(name = "email")
    private String email;
    @Setter @Getter @Column(name = "direccion")
    private String direccion;
    @Setter @Getter @Column(name = "password")
    private String password;

    @Getter @Setter @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_usuarios",
            joinColumns = @JoinColumn (name = "id_usuario", referencedColumnName = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    )
    private Set<Rol> roles;

    public Usuario(String nombre, String telefono, String email, String direccion, String password) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.password = password;
        this.roles = roles;
    }

    public Usuario(String nombre, String telefono, String email, String direccion, String password, Set<Rol> roles) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.password = password;
        this.roles = roles;
    }

    public Usuario() {
    }
}

package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String nroCelular;
    private String email;

    // Relación many-to-many con Cuenta
    @ManyToMany
    @JoinTable(
            name = "usuario_cuenta", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "usuario_id"), // Columna que referencia a Usuario
            inverseJoinColumns = @JoinColumn(name = "cuenta_id") // Columna que referencia a Cuenta
    )
    private List<Cuenta> cuentas;

    public Usuario(String nombreUsuario, String nombre, String apellido, String nroCelular, String email) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nroCelular = nroCelular;
        this.email = email;
    }
}

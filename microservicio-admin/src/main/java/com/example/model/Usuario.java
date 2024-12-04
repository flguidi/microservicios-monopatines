package com.example.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String nroCelular;
    private String email;
}

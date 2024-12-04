package com.example.dto;

import com.example.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String nroCelular;
    private String email;

    public UsuarioDto(Usuario usuario) {
        this.nombreUsuario = usuario.getNombreUsuario();
        this.nombre = usuario.getNombre();
        this.apellido = usuario.getApellido();
        this.nroCelular = usuario.getNroCelular();
        this.email = usuario.getEmail();
    }

}

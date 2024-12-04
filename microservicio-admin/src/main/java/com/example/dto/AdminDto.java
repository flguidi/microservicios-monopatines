package com.example.dto;

import com.example.entities.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    private String nombre;
    private String apellido;
    private String dni;
    private String contrasenia;

    public AdminDto(Admin admin) {
        this.nombre = admin.getNombre();
        this.apellido = admin.getApellido();
        this.dni = admin.getDni();
        this.contrasenia = admin.getContrasenia();
    }
}

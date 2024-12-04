package com.example.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    private String estado;
    private Double latitud;
    private Double longitud;
    private Double kmRecorridos;
    private Double tiempoUso;
}

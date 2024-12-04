package com.example.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    private Long id;
    private String estado;
    private Double latitud;
    private Double longitud;
    private Double kmRecorridos;
    private Double tiempoUso;
}

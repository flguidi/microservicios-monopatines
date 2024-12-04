package com.example.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Double kmRecorridos;
    private Integer minPausa;
    private Double tarifaNormal;
    private Double tarifaExtra;
    private Integer maxTiempoPausa;
}

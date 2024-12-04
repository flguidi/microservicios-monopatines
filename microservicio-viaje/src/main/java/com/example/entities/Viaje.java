package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime inicio;

    @Column(nullable = false)
    private LocalDateTime fin;

    @Column(nullable = false)
    private Double kmRecorridos;

    @Column(nullable = false)
    private Integer minPausa;

    @Column(nullable = false)
    private Double tarifaNormal;

    @Column(nullable = false)
    private Double tarifaExtra;

    @Column(nullable = false)
    private Integer maxTiempoPausa;

    @Column(nullable = false)
    private Long idMonopatin;

    @Column(nullable = false)
    private Long idCuenta;

}

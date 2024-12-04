package com.example.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Parada {
    private String nombre;
    private double latitud;
    private double longitud;
    private Integer cantMaxMonopatin;
}
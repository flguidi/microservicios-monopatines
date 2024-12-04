package com.example.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "paradas") // Especifica la colección en MongoDB
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Parada {
    @Id
    private String id;

    private String nombre;

    private double latitud;

    private double longitud;

    private Integer cantMaxMonopatin;

}
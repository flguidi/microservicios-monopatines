package com.example.utils;

import com.example.entities.Viaje;
import com.example.repository.ViajeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@Slf4j
public class CargaDatosViaje {

    @Bean
    CommandLineRunner cargarDatosViaje(ViajeRepository viajeRepository) {

        return args -> {
            log.info("Preloading " + viajeRepository.save(new Viaje(
                    null,
                    LocalDateTime.of(2024, 11, 1, 8, 0),
                    LocalDateTime.of(2024, 11, 1, 9, 30),
                    15.0,
                    10,
                    50.0,
                    75.0,
                    15,
                    1L,
                    1L
            )));
            log.info("Preloading " + viajeRepository.save(new Viaje(
                    null,
                    LocalDateTime.of(2024, 11, 2, 10, 15),
                    LocalDateTime.of(2024, 11, 2, 12, 0),
                    25.5,
                    20,
                    80.0,
                    100.0,
                    20,
                    2L,
                    2L
            )));
            log.info("Preloading " + viajeRepository.save(new Viaje(
                    null,
                    LocalDateTime.of(2024, 11, 3, 14, 30),
                    LocalDateTime.of(2024, 11, 3, 16, 0),
                    12.3,
                    5,
                    45.0,
                    60.0,
                    10,
                    3L,
                    1L
            )));
        };
    }
}

package com.example.utils;

import com.example.entities.Parada;
import com.example.repository.ParadaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CargaDatosParada {

    @Bean
    CommandLineRunner cargarDatos(@Qualifier("paradaRepository") ParadaRepository paradaRepository) {

        return args -> {
            log.info("Preloading " + paradaRepository.save(new Parada("1", "ParadaA", 20.3, 65.1, 36)));
            log.info("Preloading " + paradaRepository.save(new Parada("2", "ParadaB", 12.5, 54.3, 12)));
            log.info("Preloading " + paradaRepository.save(new Parada("3", "ParadaC", 52.8, 52.4, 24)));
            log.info("Preloading " + paradaRepository.save(new Parada("4", "ParadaD", 42.6, 72.3, 13)));
            log.info("Preloading " + paradaRepository.save(new Parada("5", "ParadaE", 25.9, 42.6, 9)));
        };

    }
}
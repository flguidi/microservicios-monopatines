package com.example.utils;

import com.example.entities.Cuenta;
import com.example.entities.Usuario;
import com.example.repository.CuentaRepository;
import com.example.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@Slf4j
public class CargaDatosCliente {

    @Bean
    CommandLineRunner cargarDatos(@Qualifier("usuarioRepository") UsuarioRepository usuarioRepository,
                                  @Qualifier("cuentaRepository") CuentaRepository cuentaRepository) {
        return args -> {
            // Guardar un nuevo Usuario
            Usuario usuario = new Usuario("armando1956", "Armando", "Paredes", "12345678", "armandoparedes@mail.com");
            log.info("Preloading " + usuarioRepository.save(usuario));

            Usuario usuario2 = new Usuario("pepe", "José", "García", "23546987", "josecito@mail.com");
            log.info("Preloading " + usuarioRepository.save(usuario2));

            // Guardar una nueva Cuenta
            Cuenta cuenta = new Cuenta(10500.0, LocalDate.of(2020, 3, 15), 9500.0, true);
            log.info("Preloading " + cuentaRepository.save(cuenta));

            Cuenta cuenta2 = new Cuenta(10800.0, LocalDate.of(2021, 6, 11), 10500.0, true);
            log.info("Preloading " + cuentaRepository.save(cuenta2));
        };
    }

}

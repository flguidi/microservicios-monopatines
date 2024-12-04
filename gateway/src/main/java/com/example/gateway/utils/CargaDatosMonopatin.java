package com.example.gateway.utils;

import com.example.gateway.entity.Authority;
import com.example.gateway.entity.User;
import com.example.gateway.repository.AuthorityRepository;
import com.example.gateway.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@Slf4j
public class CargaDatosMonopatin {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    CommandLineRunner cargarDatos(@Qualifier("authorityRepository") AuthorityRepository authorityRepository,
                                  @Qualifier("userRepository") UserRepository userRepository) {
        return args -> {


            log.info("Preloading " + authorityRepository.save(new Authority("ADMIN")));
            log.info("Preloading " + authorityRepository.save(new Authority("MANTENIMIENTO")));
            log.info("Preloading " + authorityRepository.save(new Authority("USER")));

            Authority adminAuthority = authorityRepository.findById("ADMIN").orElseThrow(() -> new RuntimeException("Authority not found: ADMIN"));
            Authority mantenimientoAuthority = authorityRepository.findById("MANTENIMIENTO").orElseThrow(() -> new RuntimeException("Authority not found: MANTENIMIENTO"));
            Authority userAuthority = authorityRepository.findById("USER").orElseThrow(() -> new RuntimeException("Authority not found: USER"));

            // Crear el primer usuario con rol ADMIN
            User adminUser = new User("Armando");
            adminUser.setPassword(passwordEncoder.encode("1234")); // Establece la contraseña
            adminUser.setAuthorities(Set.of(adminAuthority)); // Asignar la autoridad de ADMIN
            log.info("Preloading " + userRepository.save(adminUser)); // Guardar el usuario

            // Crear el segundo usuario con rol MANTENIMIENTO
            User mantenimientoUser = new User("Ricardo");
            mantenimientoUser.setPassword(passwordEncoder.encode("2345"));
            mantenimientoUser.setAuthorities(Set.of(mantenimientoAuthority));
            log.info("Preloading " + userRepository.save(mantenimientoUser));

            // Crear el tercer usuario con rol USER
            User user = new User("Carla");
            user.setPassword(passwordEncoder.encode("3456"));
            user.setAuthorities(Set.of(userAuthority));
            log.info("Preloading " + userRepository.save(user));
        };
    }

}

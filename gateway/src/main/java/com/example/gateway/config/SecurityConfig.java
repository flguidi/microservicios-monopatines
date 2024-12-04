package com.example.gateway.config;

import com.example.gateway.security.AuthorityConstant;
import com.example.gateway.security.jwt.JwtFilter;
import com.example.gateway.security.jwt.TokenProvider;
import jakarta.ws.rs.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.securityMatcher("/api/**")
                .authorizeHttpRequests(authz -> authz
                        // Permite acceso a Swagger y OpenAPI sin autenticación
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/").permitAll()

                        // Permite cualquier solicitud POST al endpoint /api/authenticate sin necesidad de autenticación
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()

                        // Permisos para cada rol
                        .requestMatchers("/api/admins/**").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers("/api/usuarios/**").hasAnyAuthority(AuthorityConstant._ADMIN, AuthorityConstant._USER)
                        .requestMatchers("/api/cuentas/**").hasAnyAuthority(AuthorityConstant._ADMIN, AuthorityConstant._USER)
                        .requestMatchers("/api/mantenimientos/**").hasAnyAuthority(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)
                        .requestMatchers("/api/monopatines/**").hasAnyAuthority(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)
                        .requestMatchers("/api/paradas/**").hasAnyAuthority(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)
                        .requestMatchers("/api/viajes/**").hasAnyAuthority(AuthorityConstant._ADMIN, AuthorityConstant._MANTENIMIENTO)

                        .anyRequest().denyAll()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new JwtFilter(this.tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}

package com.example.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "microservicio-cliente")
public interface CuentaFeign {

    // Habilita o deshabilita una cuenta
    @PutMapping("api/cuentas/estado-cuenta/{id}/estado/{estado}")
    void cambiarEstadoCuenta (@PathVariable("id") Long id, @PathVariable("estado") boolean estado);

}

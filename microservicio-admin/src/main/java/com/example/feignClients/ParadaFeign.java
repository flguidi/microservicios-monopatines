package com.example.feignClients;

import com.example.model.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservicio-parada")
public interface ParadaFeign {

    // Agrega una parada
    @PostMapping("api/paradas/")
    Parada createParada(@RequestBody Parada parada);

    // Modifica una parada
    @PutMapping("api/paradas/id/{id}")
    Parada updateParada(@RequestBody Parada parada, @PathVariable Long id);

    // Elimina una parada
    @DeleteMapping("api/paradas/id/{id}")
    Parada deleteParada(@PathVariable("id") Long id);

}

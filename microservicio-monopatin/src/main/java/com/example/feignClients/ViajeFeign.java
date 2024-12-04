package com.example.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservicio-viaje")
public interface ViajeFeign {

}

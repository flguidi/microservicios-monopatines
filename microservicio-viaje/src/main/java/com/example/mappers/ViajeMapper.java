package com.example.mappers;

import com.example.dto.ViajeDto;
import com.example.entities.Viaje;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ViajeMapper {

    // Convierte un viaje a viajeDto
    ViajeDto toViajeDto(Viaje viaje);

    // Convierte un viajeDto a viaje
    Viaje toViaje(ViajeDto viajeDto);

}


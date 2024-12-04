package com.example.mappers;

import com.example.dto.ParadaDto;
import com.example.entities.Parada;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParadaMapper {

    // Convierte una Parada a ParadaDto
    ParadaDto toParadaDto(Parada parada);

    // Convierte un ParadaDto a Parada
    Parada toParada(ParadaDto paradaDto);

}

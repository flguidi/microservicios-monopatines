package com.example.mappers;

import com.example.dto.MantenimientoDto;
import com.example.entities.Mantenimiento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MantenimientoMapper {

    MantenimientoDto toMantenimientoDto(Mantenimiento mantenimiento);

    Mantenimiento toMantenimiento(MantenimientoDto mantenimientoDto);

}

package com.example.mappers;

import com.example.dto.CuentaDto;
import com.example.entities.Cuenta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    //Convierte una cuenta a cuentaDto
    CuentaDto toCuentaDto(Cuenta cuenta);

    //Convierte una cuentaDto a cuenta
    Cuenta toCuenta(CuentaDto cuentaDto);

}

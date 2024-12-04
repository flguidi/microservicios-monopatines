package com.example.mappers;

import com.example.dto.MonopatinDto;
import com.example.entities.Monopatin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonopatinMapper {

    MonopatinDto toMonopatinDto(Monopatin monopatin);

    Monopatin toMonopatin(MonopatinDto monopatinDto);

}

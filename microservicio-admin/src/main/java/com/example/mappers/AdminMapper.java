package com.example.mappers;

import com.example.dto.AdminDto;
import com.example.entities.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    //Convierte un admin a adminDto
    AdminDto toAdminDto(Admin admin);

    //Convierte un adminDto a admin
    Admin toAdmin(AdminDto adminDto);

}


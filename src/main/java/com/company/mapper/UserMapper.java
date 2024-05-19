package com.company.mapper;

import com.company.dto.UserDTO;
import com.company.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDTO toDto(User entity);

    User toEntity(UserDTO userDTO);

    @Mapping(target = "id", ignore = true)
    User toEntity(@MappingTarget User user, UserDTO userDTO);
}
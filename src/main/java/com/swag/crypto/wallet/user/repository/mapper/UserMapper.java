package com.swag.crypto.wallet.user.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
  /**  @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User dtoToEntity(UserDto userDto);
    /** @Mapping(source = "id", target = "id")
 @Mapping(source = "firstName", target = "firstName")
 @Mapping(source = "lastName", target = "lastName")
 @Mapping(source = "email", target = "email")
 @Mapping(source = "password", target = "password")
    UserDto entityToDto(User user);
    **/
}

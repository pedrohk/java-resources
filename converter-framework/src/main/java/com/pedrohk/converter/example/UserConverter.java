package com.pedrohk.converter.example;

import com.pedrohk.converter.core.TypeConverter;

public class UserConverter implements TypeConverter<UserEntity, UserDTO> {
    @Override
    public UserDTO convert(UserEntity source) {
        return new UserDTO(
                source.firstName() + " " + source.lastName(),
                source.email()
        );
    }

    @Override
    public Class<UserEntity> getSourceType() {
        return UserEntity.class;
    }

    @Override
    public Class<UserDTO> getTargetType() {
        return UserDTO.class;
    }
}

package com.codewithahmed.ecommerce.auth;

import com.codewithahmed.ecommerce.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

//    @Mapping(target = "addresses",
//            expression = "java(java.util.Collections.singletonList(dto.getAddress()))")
    User toUser(RegisterDto dto);

    UserAuthResponseDto toDto(User user);
}

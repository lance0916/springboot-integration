package com.rabbit.jmh;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author WuQinglong
 * @since 2023/11/7 9:49 AM
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO copy(UserDTO dto);

}

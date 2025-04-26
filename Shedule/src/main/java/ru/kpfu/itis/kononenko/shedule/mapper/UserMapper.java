package ru.kpfu.itis.kononenko.shedule.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.kpfu.itis.kononenko.shedule.dto.SignUpDto;
import ru.kpfu.itis.kononenko.shedule.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    
    User mapToUser(SignUpDto signUpDto, String hashPassword);
}

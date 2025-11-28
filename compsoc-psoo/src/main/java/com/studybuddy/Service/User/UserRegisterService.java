package com.studybuddy.Service.User;

import com.studybuddy.Dto.Student.UserDto;
import com.studybuddy.Entity.UserEntity;

public interface UserRegisterService {
    UserEntity register(UserDto userDto);
}

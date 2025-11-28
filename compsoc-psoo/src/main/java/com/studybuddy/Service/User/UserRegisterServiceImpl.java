package com.studybuddy.Service.User;

import com.studybuddy.Dto.Student.UserDto;
import com.studybuddy.Entity.UserEntity;
import com.studybuddy.Repository.User.UserEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserEntityRepository userEntityRepository;

    public UserRegisterServiceImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserEntity register(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPasswordHash(userDto.getPassword());
        return userEntityRepository.save(userEntity);
    }
}

package com.studybuddy.Service.User;

import com.studybuddy.Dto.Student.RegisterDto;
import com.studybuddy.Dto.Student.StudentDto;
import com.studybuddy.Dto.Student.UserDto;
import com.studybuddy.Entity.StudentEntity;
import com.studybuddy.Entity.UserEntity;
import com.studybuddy.Repository.Student.StudentEntityRepository;
import com.studybuddy.Repository.User.UserEntityRepository;
import com.studybuddy.Service.Student.StudentRegisterService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final StudentRegisterService studentRegisterService;
    private final UserRegisterService userRegisterService;
    private final StudentEntityRepository studentEntityRepository;
    private final UserEntityRepository userEntityRepository;

    public RegisterServiceImpl(StudentRegisterService studentRegisterService,
                               UserRegisterService userRegisterService,
                               StudentEntityRepository studentEntityRepository, UserEntityRepository userEntityRepository) {
        this.studentRegisterService = studentRegisterService;
        this.userRegisterService = userRegisterService;
        this.studentEntityRepository = studentEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    @Transactional
    public void create(RegisterDto registerDto) {

        StudentDto studentDto = new StudentDto();
        studentDto.setName(registerDto.getName());
        studentDto.setUniversity(registerDto.getUniversity());
        studentDto.setName(registerDto.getName());

        UserDto userDto = new UserDto();
        userDto.setEmail(registerDto.getEmail());
        userDto.setPassword(registerDto.getPassword());

        UserEntity user = userRegisterService.register(userDto);
        StudentEntity student = studentRegisterService.register(studentDto, user);

        bindEntities(student, user);
    }

    private void bindEntities(StudentEntity student, UserEntity user){
        user.setStudent(student);
        userEntityRepository.save(user);
    }
}

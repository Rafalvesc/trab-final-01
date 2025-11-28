package com.studybuddy.Service.Student;

import com.studybuddy.Dto.Student.StudentDto;
import com.studybuddy.Entity.StudentEntity;
import com.studybuddy.Entity.UserEntity;

public interface StudentRegisterService {
    StudentEntity register(StudentDto studentDto, UserEntity userEntity);
}

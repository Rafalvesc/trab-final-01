package com.studybuddy.Service.Student;

import com.studybuddy.Dto.Student.StudentDto;
import com.studybuddy.Entity.StudentEntity;
import com.studybuddy.Entity.UserEntity;
import com.studybuddy.Repository.Student.StudentEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudentRegisterServiceImpl implements StudentRegisterService {
    private final StudentEntityRepository studentEntityRepository;

    public StudentRegisterServiceImpl(StudentEntityRepository studentEntityRepository) {
        this.studentEntityRepository = studentEntityRepository;
    }

    @Override
    public StudentEntity register(StudentDto studentDto, UserEntity userEntity) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(studentDto.getName());
        studentEntity.setUserEntity(userEntity);
        studentEntity.setUniversity(studentDto.getUniversity());
        studentEntity.setStudyflowEntities(new ArrayList<>());
        return studentEntityRepository.save(studentEntity);
    }
}

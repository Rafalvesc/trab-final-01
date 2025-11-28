package com.studybuddy.Service.Student;

import com.studybuddy.Entity.StudentEntity;
import com.studybuddy.Repository.Student.StudentEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetStudentService {
    private final StudentEntityRepository studentEntityRepository;
    public GetStudentService(StudentEntityRepository studentEntityRepository) {
        this.studentEntityRepository = studentEntityRepository;
    }

    public StudentEntity get(UUID studentId){
        Optional<StudentEntity> studentEntity = studentEntityRepository.findById(studentId);
        if(studentEntity.isPresent()){
            return studentEntity.get();
        }
        else throw new EntityNotFoundException("Estudante não encontrado");
    }
}

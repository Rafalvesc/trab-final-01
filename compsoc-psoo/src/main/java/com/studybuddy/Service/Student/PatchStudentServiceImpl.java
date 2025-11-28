package com.studybuddy.Service.Student;

import com.studybuddy.Dto.Student.StudentPatchDto;
import com.studybuddy.Entity.StudentEntity;
import com.studybuddy.Exception.GenericException;
import com.studybuddy.Repository.Student.StudentEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class PatchStudentServiceImpl implements PatchStudentService {
    private final StudentEntityRepository studentEntityRepository;

    public PatchStudentServiceImpl(StudentEntityRepository studentEntityRepository) {
        this.studentEntityRepository = studentEntityRepository;
    }

    @Override
    public void patch(StudentPatchDto studentPatchDto) {
        Optional<StudentEntity> studentEntityOptional = studentEntityRepository.findById(studentPatchDto.getId());
        if (studentEntityOptional.isEmpty()) {
            throw new GenericException("erro ao encontrar usuário");
        }
        studentEntityOptional.get().setUniversity(studentPatchDto.getUniversity());
        studentEntityOptional.get().setName(studentPatchDto.getName());
        studentEntityRepository.save(studentEntityOptional.get());
    }
}

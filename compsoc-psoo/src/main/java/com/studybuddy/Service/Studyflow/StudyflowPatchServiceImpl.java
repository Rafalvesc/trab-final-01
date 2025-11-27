package com.studybuddy.Service.Studyflow;

import com.studybuddy.Dto.Studyflow.StudyflowPatchDto;
import com.studybuddy.Entity.StudyflowEntity;
import com.studybuddy.Exception.GenericException;
import com.studybuddy.Repository.Studyflow.StudyflowEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyflowPatchServiceImpl implements StudyflowPatchService {
    private final StudyflowEntityRepository studyflowEntityRepository;

    public StudyflowPatchServiceImpl(StudyflowEntityRepository studyflowEntityRepository) {
        this.studyflowEntityRepository = studyflowEntityRepository;
    }

    @Override
    @Transactional
    public void patch(StudyflowPatchDto studyflowPatchDto) {
        Optional<StudyflowEntity> studyflowEntityOptional = studyflowEntityRepository.findById(studyflowPatchDto.getId());
        if(studyflowEntityOptional.isEmpty()){
            throw new GenericException("nenhum studyflow com esse id");
        }
        studyflowEntityOptional.get().setTitle(studyflowPatchDto.getTitle());
        studyflowEntityOptional.get().setDescription(studyflowPatchDto.getDescription());
        studyflowEntityRepository.save(studyflowEntityOptional.get());
    }

}

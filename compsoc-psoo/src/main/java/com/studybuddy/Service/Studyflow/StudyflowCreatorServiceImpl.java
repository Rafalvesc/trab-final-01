package com.studybuddy.Service.Studyflow;

import com.studybuddy.Dto.Studyflow.StudyflowPostDto;
import com.studybuddy.Entity.StudyflowEntity;
import com.studybuddy.Repository.Studyflow.StudyflowEntityRepository;
import com.studybuddy.Service.Student.GetStudentService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class StudyflowCreatorServiceImpl implements StudyflowCreatorService {

    private final StudyflowEntityRepository studyflowEntityRepository;
    private final GetStudentService getStudentService;

    public StudyflowCreatorServiceImpl(StudyflowEntityRepository studyflowEntityRepository,
                                       GetStudentService getStudentService) {
        this.studyflowEntityRepository = studyflowEntityRepository;
        this.getStudentService = getStudentService;
    }

    @Override
    public UUID create(StudyflowPostDto studyflowPostDto) {
        StudyflowEntity studyflowEntity = new StudyflowEntity();
        initialize(studyflowEntity, studyflowPostDto);
        return save(studyflowEntity).getId();
    }

    private StudyflowEntity save(StudyflowEntity studyflowEntity) {
        return studyflowEntityRepository.save(studyflowEntity);
    }

    private void initialize(StudyflowEntity studyflowEntity, StudyflowPostDto studyflowPostDto) {
        studyflowEntity.setDescription(studyflowPostDto.getDescription());
        studyflowEntity.setStudent(getStudentService.get(studyflowPostDto.getStudentId()));
        studyflowEntity.setTitle(studyflowPostDto.getTitle());
        studyflowEntity.setCreatedAt(Instant.now());
        studyflowEntity.setQuestions(new ArrayList<>());
        studyflowEntity.setResources(new ArrayList<>());
    }
}

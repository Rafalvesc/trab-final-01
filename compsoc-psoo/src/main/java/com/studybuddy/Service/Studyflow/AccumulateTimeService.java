package com.studybuddy.Service.Studyflow;

import com.studybuddy.Dto.Studyflow.StudyflowTimeDto;
import com.studybuddy.Entity.StudyflowEntity;
import com.studybuddy.Exception.GenericException;
import com.studybuddy.Repository.Studyflow.StudyflowEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccumulateTimeService {
    private final StudyflowEntityRepository studyflowEntityRepository;

    public AccumulateTimeService(StudyflowEntityRepository studyflowEntityRepository) {
        this.studyflowEntityRepository = studyflowEntityRepository;
    }


    @Transactional
    public void accumulateTime(StudyflowTimeDto dto) {
        UUID id = dto.getStudyflowId();
        if (id == null) throw new GenericException("studyflowId obrigatório");


        StudyflowEntity entity = studyflowEntityRepository.findById(id)
                .orElseThrow(() -> new GenericException("Studyflow não encontrado: " + id));

        Long current = entity.getTimeSpent() == null ? 0 : entity.getTimeSpent();
        entity.setTimeSpent(current + dto.getMillis());

        studyflowEntityRepository.save(entity);
    }
}

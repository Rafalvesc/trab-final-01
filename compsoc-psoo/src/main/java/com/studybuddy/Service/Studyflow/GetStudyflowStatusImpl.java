package com.studybuddy.Service.Studyflow;

import com.studybuddy.Dto.Studyflow.Indicator.IndicatorDto;
import com.studybuddy.Dto.Studyflow.StudyflowStatusDto;
import com.studybuddy.Entity.IndicatorEntity;
import com.studybuddy.Entity.StudyflowEntity;
import com.studybuddy.Exception.GenericException;
import com.studybuddy.Repository.Studyflow.Indicator.IndicatorEntityRepository;
import com.studybuddy.Repository.Studyflow.StudyflowEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetStudyflowStatusImpl implements GetStudyflowStatus {
    private final StudyflowEntityRepository studyflowEntityRepository;
    private final IndicatorEntityRepository indicatorEntityRepository;

    public GetStudyflowStatusImpl(StudyflowEntityRepository studyflowEntityRepository, IndicatorEntityRepository indicatorEntityRepository) {
        this.studyflowEntityRepository = studyflowEntityRepository;
        this.indicatorEntityRepository = indicatorEntityRepository;
    }

    @Override
    public StudyflowStatusDto get(UUID studyflowId) {
        List<IndicatorDto> indicatorDtoList = new ArrayList();
        StudyflowStatusDto studyflowStatusDto = new StudyflowStatusDto();
        Optional<StudyflowEntity> studyflowEntityOptional = studyflowEntityRepository.findById(studyflowId);
        if (studyflowEntityOptional.isEmpty()){
            throw new GenericException("nao encontrado");
        }
        List<IndicatorEntity> indicatorEntityList = indicatorEntityRepository.findAllByStudyflowId(studyflowEntityOptional.get().getId());
        if (indicatorEntityList.isEmpty()) {
            throw new GenericException("indicadores ainda nao gerados");
        }
        for (IndicatorEntity indicatorEntity : indicatorEntityList){
            IndicatorDto indicatorDto = new IndicatorDto();
            indicatorDto.setIndicatorTag(indicatorEntity.getIndicatorTag());
            indicatorDto.setCorrectCount(indicatorEntity.getCorrectCount());
            indicatorDto.setAnsweredCount(indicatorEntity.getAnsweredCount());
            indicatorDtoList.add(indicatorDto);
        }
        studyflowStatusDto.setIndicatorDtoList(indicatorDtoList);
        studyflowStatusDto.setTimeSpent(studyflowEntityOptional.get().getTimeSpent());
        return studyflowStatusDto;
    }
}

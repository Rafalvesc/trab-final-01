package com.studybuddy.Service.Studyflow;

import com.studybuddy.Dto.Studyflow.StudyflowGetDto;
import com.studybuddy.Entity.IndicatorEntity;
import com.studybuddy.Entity.StudyflowEntity;
import com.studybuddy.Exception.GenericException;
import com.studybuddy.Repository.Studyflow.Indicator.IndicatorEntityRepository;
import com.studybuddy.Repository.Studyflow.StudyflowEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetAllStudyflowsImpl implements GetAllStudyflows {
    private final StudyflowEntityRepository studyflowEntityRepository;
    private final IndicatorEntityRepository indicatorEntityRepository;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            .withZone(ZoneId.systemDefault());


    public GetAllStudyflowsImpl(StudyflowEntityRepository studyflowEntityRepository, IndicatorEntityRepository indicatorEntityRepository) {
        this.studyflowEntityRepository = studyflowEntityRepository;
        this.indicatorEntityRepository = indicatorEntityRepository;
    }

    @Override
    @Transactional
    public List<StudyflowGetDto> get(UUID studentId) {
        List<StudyflowEntity> studyflowEntityList = studyflowEntityRepository.findAllByStudentEntity_Id(studentId);

        if(studyflowEntityList.isEmpty()){
            return List.of();
        }

        List<StudyflowGetDto> studyflowGetDtoList = new ArrayList<>();
        for (StudyflowEntity studyflowEntity : studyflowEntityList) {
            StudyflowGetDto studyflowGetDto = new StudyflowGetDto();
            studyflowGetDto.setDescription(studyflowEntity.getDescription());
            studyflowGetDto.setCreationDate(formatter.format(studyflowEntity.getCreatedAt()));
            studyflowGetDto.setTitle(studyflowEntity.getTitle());
            studyflowGetDto.setMainTags(getTagsByStudyflowEntity(studyflowEntity.getId()));
            studyflowGetDto.setId(studyflowEntity.getId());
            studyflowGetDtoList.add(studyflowGetDto);
        }
        return studyflowGetDtoList;
    }

    private List<String> getTagsByStudyflowEntity(UUID studyflowEntityId) {
        List<IndicatorEntity> indicatorEntityList = indicatorEntityRepository.findAllByStudyflowId(studyflowEntityId);
        List<String> tags = new ArrayList<>();
        if (indicatorEntityList.isEmpty()){
            throw new GenericException("nao encontrado indicadores para esse studflow");
        }
        for (IndicatorEntity indicatorEntity : indicatorEntityList) {
            tags.add(indicatorEntity.getIndicatorTag());
        }
        return tags;
    }
}

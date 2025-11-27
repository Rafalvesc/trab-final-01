package com.studybuddy.Service.Studyflow.Resource;

import com.studybuddy.Dto.Studyflow.ResourceDto;
import com.studybuddy.Entity.StudyflowEntity;
import com.studybuddy.Repository.Studyflow.Resource.ResourceEntityRepository;
import com.studybuddy.Repository.Studyflow.StudyflowEntityRepository;
import com.studybuddy.Service.Studyflow.Indicator.GenerateIndicatorService;
import com.studybuddy.entity.ResourceEntity;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Objects;

@Service
public class PostResourceServiceImpl implements PostResourceService {
    private static final Logger log = LoggerFactory.getLogger(PostResourceServiceImpl.class);

    private final ResourceEntityRepository resourceEntityRepository;
    private final StudyflowEntityRepository studyflowEntityRepository;
    private final GenerateIndicatorService generateIndicatorService;
    private boolean foundIndicator = false;

    public PostResourceServiceImpl(ResourceEntityRepository resourceEntityRepository,
                                   ApplicationEventPublisher publisher,
                                   StudyflowEntityRepository studyflowEntityRepository, GenerateIndicatorService generateIndicatorService) {
        this.resourceEntityRepository = resourceEntityRepository;
        this.studyflowEntityRepository = studyflowEntityRepository;
        this.generateIndicatorService = generateIndicatorService;
    }

    @Transactional
    @Override
    public void createBatch(List<ResourceDto> resourceDtoList){
        if (resourceDtoList == null || resourceDtoList.isEmpty()) {
            throw new IllegalArgumentException("resourceDtoList is null or empty");
        }

        UUID studyflowId = resourceDtoList.get(0).getStudyflowId();
        if (studyflowId == null) {
            throw new IllegalArgumentException("studyflowId is required in resource DTOs");
        }

        for (ResourceDto resourceDto : resourceDtoList) {
            create(resourceDto);
        }

        generateIndicatorService.generate(studyflowId);
    }

    private void create(ResourceDto resourceDto) {
        Objects.requireNonNull(resourceDto, "resourceDto");
        if (resourceDto.getBinary() == null) {
            throw new IllegalArgumentException("binary (file bytes) is required for file: " + resourceDto.getFilename());
        }

        ResourceEntity resourceEntity = new ResourceEntity();
        resourceEntity.setStudyFlowId(resourceDto.getStudyflowId());
        resourceEntity.setFilename(resourceDto.getFilename());
        resourceEntity.setMimeType(resourceDto.getMime());
        resourceEntity.setFileData(resourceDto.getBinary());
        resourceEntity.setSizeBytes(resourceEntity.getFileData() != null ? resourceEntity.getFileData().length : 0);

        log.info("Saving resource: {} ({} bytes) for studyflow {}", resourceEntity.getFilename(), resourceEntity.getSizeBytes(), resourceEntity.getStudyFlowId());

        ResourceEntity saved = resourceEntityRepository.save(resourceEntity);
        log.info("Saved resource id={} filename={}", saved.getId(), saved.getFilename());

        if (resourceDto.isIndicator()){
            log.info("User sent studyflow indicador");
            saveIndicator(resourceEntity);
            foundIndicator = true;
        }
    }

    private void saveIndicator(ResourceEntity resourceEntity) {
        Optional<StudyflowEntity> studyflowEntityOptional = studyflowEntityRepository.findById(resourceEntity.getStudyFlowId());
        if (studyflowEntityOptional.isPresent()) {
            studyflowEntityOptional.get().setIndicator(resourceEntity);
        }
    }
}

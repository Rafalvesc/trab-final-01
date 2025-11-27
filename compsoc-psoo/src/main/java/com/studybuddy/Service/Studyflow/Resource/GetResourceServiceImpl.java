package com.studybuddy.Service.Studyflow.Resource;

import com.studybuddy.Dto.Studyflow.ResourceDto;
import com.studybuddy.Exception.GenericException;
import com.studybuddy.Repository.Studyflow.Resource.ResourceEntityRepository;
import com.studybuddy.entity.ResourceEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetResourceServiceImpl implements GetResourceService {
    private final ResourceEntityRepository resourceEntityRepository;

    public GetResourceServiceImpl(ResourceEntityRepository resourceEntityRepository) {
        this.resourceEntityRepository = resourceEntityRepository;
    }

    @Override
    public List<ResourceDto> getAll(UUID studyflowId) {
        List<ResourceEntity> resourceEntityList = resourceEntityRepository.findAllByStudyFlowId(studyflowId);
        if (resourceEntityList.isEmpty()) {
            throw new GenericException("resourceentity vazia");
        }
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        resourceEntityList.forEach(resourceEntity -> {
            ResourceDto resourceDto = new ResourceDto();
            resourceDto.setBinary(resourceEntity.getFileData());
            resourceDto.setStudyflowId(studyflowId);
            resourceDto.setFilename(resourceEntity.getFilename());
            resourceDto.setMime(resourceEntity.getMimeType());
            resourceDtoList.add(resourceDto);
        });
        return resourceDtoList;
    }
}

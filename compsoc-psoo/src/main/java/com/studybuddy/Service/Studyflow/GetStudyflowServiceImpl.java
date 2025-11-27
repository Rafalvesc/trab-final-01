package com.studybuddy.Service.Studyflow;

import com.studybuddy.Dto.Studyflow.ResourceDto;
import com.studybuddy.Dto.Studyflow.StudyflowFullDto;
import com.studybuddy.Entity.StudyflowEntity;
import com.studybuddy.Exception.GenericException;
import com.studybuddy.Repository.Studyflow.Resource.ResourceEntityRepository;
import com.studybuddy.Repository.Studyflow.StudyflowEntityRepository;
import com.studybuddy.Service.Studyflow.Resource.GetResourceService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetStudyflowServiceImpl implements GetStudyflowService {
    private final StudyflowEntityRepository studyflowEntityRepository;
    private final ResourceEntityRepository resourceEntityRepository;
    private final GetResourceService getResourceService;
    private final GetMainTagsService getMainTagsService;

    public GetStudyflowServiceImpl(StudyflowEntityRepository studyflowEntityRepository, ResourceEntityRepository resourceEntityRepository, GetResourceService getResourceService, GetMainTagsService getMainTagsService) {
        this.studyflowEntityRepository = studyflowEntityRepository;
        this.resourceEntityRepository = resourceEntityRepository;
        this.getResourceService = getResourceService;
        this.getMainTagsService = getMainTagsService;
    }

    @Override
    @Transactional
    public StudyflowFullDto get(UUID studyflowId) {
        Optional<StudyflowEntity> studyflowEntityOptional = studyflowEntityRepository.findById(studyflowId);
        if (studyflowEntityOptional.isEmpty()){
            throw new GenericException("blablabla");
        }
        StudyflowFullDto studyflowFullDto = new StudyflowFullDto();
        studyflowFullDto.setDescription(studyflowEntityOptional.get().getDescription());
        studyflowFullDto.setId(studyflowEntityOptional.get().getId());
        studyflowFullDto.setResources(getResourceService.getAll(studyflowEntityOptional.get().getId()));
        findIndicator(studyflowFullDto.getResources(), studyflowEntityOptional.get());
        studyflowFullDto.setMainTags(getMainTagsService.getTags(studyflowId));
        studyflowFullDto.setTitle(studyflowEntityOptional.get().getTitle());
        studyflowFullDto.setCreationDate(studyflowEntityOptional.get().getCreatedAt().toString());
        return studyflowFullDto;
    }

    void findIndicator(List<ResourceDto> studyflowFullDto, StudyflowEntity studyflowEntity) {
        if (studyflowEntity.getIndicator() == null){
            return;
        }

        for(ResourceDto resourceDto : studyflowFullDto) {
            if (studyflowEntity.getIndicator().getFilename().equals(resourceDto.getFilename())){
                resourceDto.setIndicator(true);
            }
        }
    }


}

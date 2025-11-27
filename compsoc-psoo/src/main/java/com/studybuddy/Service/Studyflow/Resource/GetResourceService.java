package com.studybuddy.Service.Studyflow.Resource;

import com.studybuddy.Dto.Studyflow.ResourceDto;

import java.util.List;
import java.util.UUID;

public interface GetResourceService {
    List<ResourceDto> getAll(UUID studyflowId);
}

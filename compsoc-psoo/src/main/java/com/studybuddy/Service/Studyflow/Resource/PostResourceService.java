package com.studybuddy.Service.Studyflow.Resource;

import com.studybuddy.Dto.Studyflow.ResourceDto;
import java.util.List;

public interface PostResourceService {
    void createBatch(List<ResourceDto> resourceDtoList);
}

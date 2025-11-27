package com.studybuddy.Service.Studyflow;

import com.studybuddy.Dto.Studyflow.StudyflowFullDto;

import java.util.UUID;

public interface GetStudyflowService {
    StudyflowFullDto get(UUID studyflowId);
}

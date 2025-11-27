package com.studybuddy.Service.Studyflow;

import com.studybuddy.Dto.Studyflow.StudyflowStatusDto;

import java.util.List;
import java.util.UUID;

public interface GetStudyflowStatus {
    StudyflowStatusDto get(UUID studyflowId);
}

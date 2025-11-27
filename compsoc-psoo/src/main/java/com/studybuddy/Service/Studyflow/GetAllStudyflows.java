package com.studybuddy.Service.Studyflow;

import com.studybuddy.Dto.Studyflow.StudyflowGetDto;

import java.util.List;
import java.util.UUID;

public interface GetAllStudyflows {
    List<StudyflowGetDto> get(UUID studentId);
}

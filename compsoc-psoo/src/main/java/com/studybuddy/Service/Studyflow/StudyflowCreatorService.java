package com.studybuddy.Service.Studyflow;

import com.studybuddy.Dto.Studyflow.StudyflowPostDto;

import java.util.UUID;

public interface StudyflowCreatorService {
    UUID create(StudyflowPostDto studyflowPostDto);
}

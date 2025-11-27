package com.studybuddy.Service.Studyflow.Question;

import com.studybuddy.Dto.Studyflow.Question.QuestionGetDto;

import java.util.List;
import java.util.UUID;

public interface GetQuestionsByStudyflowId {
    List<QuestionGetDto> get(UUID studyflowId);
}

package com.studybuddy.Dto.Studyflow;

import java.util.UUID;

public class StudyflowTimeDto {
    Long millis;
    UUID studyflowId;

    public Long getMillis() {
        return millis;
    }

    public void setMillis(Long millis) {
        this.millis = millis;
    }

    public UUID getStudyflowId() {
        return studyflowId;
    }

    public void setStudyflowId(UUID studyflowId) {
        this.studyflowId = studyflowId;
    }
}

package com.studybuddy.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class IndicatorEntity {
    @Id
    private UUID id;
    private String indicatorTag;
    private Integer correctCount;
    private Integer reviewCount;
    private Integer answeredCount;
    private UUID studyflowId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIndicatorTag() {
        return indicatorTag;
    }

    public void setIndicatorTag(String indicatorTag) {
        this.indicatorTag = indicatorTag;
    }

    public Integer getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getAnsweredCount() {
        return answeredCount;
    }

    public void setAnsweredCount(Integer answeredCount) {
        this.answeredCount = answeredCount;
    }

    public UUID getStudyflowId() {
        return studyflowId;
    }

    public void setStudyflowId(UUID studyflowId) {
        this.studyflowId = studyflowId;
    }
}

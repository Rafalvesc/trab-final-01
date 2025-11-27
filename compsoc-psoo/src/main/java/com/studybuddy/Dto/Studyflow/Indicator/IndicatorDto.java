package com.studybuddy.Dto.Studyflow.Indicator;

public class IndicatorDto {
    private String indicatorTag;
    private Integer correctCount;
    private Integer answeredCount;

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

    public Integer getAnsweredCount() {
        return answeredCount;
    }

    public void setAnsweredCount(Integer answeredCount) {
        this.answeredCount = answeredCount;
    }
}

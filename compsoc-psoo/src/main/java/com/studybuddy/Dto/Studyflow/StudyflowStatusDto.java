package com.studybuddy.Dto.Studyflow;

import com.studybuddy.Dto.Studyflow.Indicator.IndicatorDto;

import java.time.Duration;
import java.util.List;

public class StudyflowStatusDto {
    private List<IndicatorDto> indicatorDtoList;
    private Long timeSpent;

    public List<IndicatorDto> getIndicatorDtoList() {
        return indicatorDtoList;
    }

    public void setIndicatorDtoList(List<IndicatorDto> indicatorDtoList) {
        this.indicatorDtoList = indicatorDtoList;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }
}

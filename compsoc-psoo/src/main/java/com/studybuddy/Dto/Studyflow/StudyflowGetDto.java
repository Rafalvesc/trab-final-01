package com.studybuddy.Dto.Studyflow;

import java.util.List;
import java.util.UUID;

public class StudyflowGetDto {
    private UUID id;
    private String title;
    private String description;
    private List<String> mainTags;
    private String creationDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMainTags() {
        return mainTags;
    }

    public void setMainTags(List<String> mainTags) {
        this.mainTags = mainTags;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}

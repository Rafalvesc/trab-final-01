package com.studybuddy.Dto.Studyflow;

import java.util.List;
import java.util.UUID;

public class StudyflowFullDto {
    private UUID id;
    private String title;
    private String description;
    private List<String> mainTags;
    private String creationDate;
    private List<ResourceDto> resources;
    private String status;

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

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
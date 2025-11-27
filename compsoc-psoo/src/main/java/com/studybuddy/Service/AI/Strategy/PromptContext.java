package com.studybuddy.Service.AI.Strategy;

import java.util.List;

public class PromptContext {
    private final String indicatorText;
    private final List<String> resourceContents;
    private final List<String> existingIndicators;

    private PromptContext(Builder builder) {
        this.indicatorText = builder.indicatorText;
        this.resourceContents = builder.resourceContents;
        this.existingIndicators = builder.existingIndicators;
    }

    public String getIndicatorText() {
        return indicatorText;
    }

    public List<String> getResourceContents() {
        return resourceContents;
    }

    public List<String> getExistingIndicators() {
        return existingIndicators;
    }

    public boolean hasIndicatorText() {
        return indicatorText != null && !indicatorText.isBlank();
    }

    public boolean hasResources() {
        return resourceContents != null && !resourceContents.isEmpty();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String indicatorText;
        private List<String> resourceContents;
        private List<String> existingIndicators;

        public Builder indicatorText(String indicatorText) {
            this.indicatorText = indicatorText;
            return this;
        }

        public Builder resourceContents(List<String> resourceContents) {
            this.resourceContents = resourceContents;
            return this;
        }

        public Builder existingIndicators(List<String> existingIndicators) {
            this.existingIndicators = existingIndicators;
            return this;
        }

        public PromptContext build() {
            return new PromptContext(this);
        }
    }
}

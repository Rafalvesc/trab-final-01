package com.studybuddy.Service.AI;

import java.util.ArrayList;
import java.util.List;

public class AiRequest {
    private final String model;
    private final List<Message> messages;
    private final double temperature;
    private final int maxTokens;

    private AiRequest(Builder builder) {
        this.model = builder.model;
        this.messages = new ArrayList<>(builder.messages);
        this.temperature = builder.temperature;
        this.maxTokens = builder.maxTokens;
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public double getTemperature() {
        return temperature;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public static class Message {
        private final String role;
        private final String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public String getContent() {
            return content;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String model = "tngtech/deepseek-r1t2-chimera:free";
        private List<Message> messages = new ArrayList<>();
        private double temperature = 0.0;
        private int maxTokens = 2000;

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder addSystemMessage(String content) {
            this.messages.add(new Message("system", content));
            return this;
        }

        public Builder addUserMessage(String content) {
            this.messages.add(new Message("user", content));
            return this;
        }

        public Builder temperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder maxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public AiRequest build() {
            if (messages.isEmpty()) {
                throw new IllegalStateException("At least one message is required");
            }
            return new AiRequest(this);
        }
    }
}

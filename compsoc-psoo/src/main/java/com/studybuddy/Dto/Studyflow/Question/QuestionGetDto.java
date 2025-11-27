package com.studybuddy.Dto.Studyflow.Question;

import com.studybuddy.Entity.QuestionType;

import java.time.Instant;
import java.util.UUID;

public class QuestionGetDto {
    private UUID id;
    private UUID studyflowId;
    private QuestionType type;
    private String expectedAnswer;
    private Boolean correct;
    private String answers;
    private String question;
    private boolean answered;
    private String tags;
    private Instant lastReviewedAt;

    public Boolean isCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStudyflowId() {
        return studyflowId;
    }

    public void setStudyflowId(UUID studyflowId) {
        this.studyflowId = studyflowId;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Instant getLastReviewedAt() {
        return lastReviewedAt;
    }

    public void setLastReviewedAt(Instant lastReviewedAt) {
        this.lastReviewedAt = lastReviewedAt;
    }
}

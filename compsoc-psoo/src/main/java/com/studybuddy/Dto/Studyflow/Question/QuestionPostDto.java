package com.studybuddy.Dto.Studyflow.Question;

import java.util.UUID;

public class QuestionPostDto {
    private UUID id;
    private String userAnswer;
    private boolean correct;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}

package com.studybuddy.Service.AI;

public class AiResponse {
    private final String content;
    private final int statusCode;
    private final String rawResponse;

    public AiResponse(String content, int statusCode, String rawResponse) {
        this.content = content;
        this.statusCode = statusCode;
        this.rawResponse = rawResponse;
    }

    public String getContent() {
        return content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }
}

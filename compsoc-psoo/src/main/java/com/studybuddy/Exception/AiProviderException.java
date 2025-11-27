package com.studybuddy.Exception;

public class AiProviderException extends StudyBuddyException {
    public AiProviderException(String message) {
        super(message, "AI_PROVIDER_ERROR");
    }

    public AiProviderException(String message, Throwable cause) {
        super(message, "AI_PROVIDER_ERROR", cause);
    }
}

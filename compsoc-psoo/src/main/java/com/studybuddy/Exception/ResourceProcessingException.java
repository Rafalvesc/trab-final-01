package com.studybuddy.Exception;

public class ResourceProcessingException extends StudyBuddyException {
    public ResourceProcessingException(String message) {
        super(message, "RESOURCE_PROCESSING_ERROR");
    }

    public ResourceProcessingException(String message, Throwable cause) {
        super(message, "RESOURCE_PROCESSING_ERROR", cause);
    }
}

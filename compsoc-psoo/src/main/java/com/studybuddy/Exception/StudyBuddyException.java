package com.studybuddy.Exception;

public abstract class StudyBuddyException extends RuntimeException {
    private final String errorCode;

    public StudyBuddyException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public StudyBuddyException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

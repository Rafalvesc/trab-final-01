package com.studybuddy.Exception;

public class EntityNotFoundException extends StudyBuddyException {
    public EntityNotFoundException(String entityType, String entityId) {
        super(String.format("%s not found with id: %s", entityType, entityId), "ENTITY_NOT_FOUND");
    }
}

package com.studybuddy.Service.AI.Strategy;

import java.util.List;

public interface PromptStrategy {
    String buildPrompt(PromptContext context);
}

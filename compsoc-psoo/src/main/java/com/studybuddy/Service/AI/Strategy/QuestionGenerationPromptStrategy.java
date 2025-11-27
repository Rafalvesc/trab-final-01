package com.studybuddy.Service.AI.Strategy;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionGenerationPromptStrategy implements PromptStrategy {
    private static final int PER_RESOURCE_CHAR_LIMIT = 10000;
    private static final int MAX_RESOURCES = 6;

    @Override
    public String buildPrompt(PromptContext context) {
        String instruction = buildInstructionText(context.getExistingIndicators());
        String userContent = buildUserContent(context);
        
        return "INSTRUCTION:\n" + instruction + "\n\nCONTEXT:\n" + userContent;
    }

    private String buildInstructionText(List<String> indicators) {
        StringBuilder sb = new StringBuilder();
        sb.append("You are a question generator that will receive a list of indicators (a Java List<String>) appended after this prompt.\\n");
        sb.append("Your TASK: generate AT MOST 30 questions derived from those indicators. RETURN ONLY a single JSON ARRAY and NOTHING ELSE.\\n");
        sb.append("Array items must be objects with these exact fields: ");
        sb.append("id (unique string), ");
        sb.append("type (one of MULTIPLE_CHOICE|SHORT_ANSWER|TRUE_FALSE|FILL_IN_THE_BLANK), ");
        sb.append("question (string), ");
        sb.append("expectedAnswer (string), ");
        sb.append("answers (array of strings, optional — required for MULTIPLE_CHOICE), ");
        sb.append("difficulty (integer 1..5), ");
        sb.append("tags (array of strings).\\n");
        sb.append("CONSTRAINTS:\\n");
        sb.append("- Return no more than 30 question objects.\\n");
        sb.append("- Each question and each expectedAnswer must be <= 400 characters.\\n");
        sb.append("- For MULTIPLE_CHOICE, provide at least 2 and at most 6 answers; the expectedAnswer must exactly match one entry in answers.\\n");
        sb.append("- difficulty must be an integer between 1 and 5.\\n");
        sb.append("- tags MUST be taken only from the provided indicators list and must match the indicator strings EXACTLY (case-sensitive, no changes).\\n");
        sb.append("- A question may list multiple tags (it's encouraged — studyflows can contain many indicators).\\n");
        sb.append("- Do not invent or change indicators — use them verbatim as provided.\\n");
        sb.append("- Keep questions focused and unambiguous.\\n");
        sb.append("- Provide diverse types (mix of MULTIPLE_CHOICE, SHORT_ANSWER, TRUE_FALSE, FILL_IN_THE_BLANK) where applicable.\\n");
        sb.append("- id should be unique per question (any consistent string scheme is fine).\\n");
        sb.append("- Avoid duplicate or near-duplicate questions.\\n");
        sb.append("QUALITY CHECK (required before returning): reread the indicators you were given and your JSON. ");
        sb.append("Have you omitted any meaningful indicator? Did you duplicate content? Are tags exact matches? If any issue, fix it — then send the perfect final JSON.\\n");
        sb.append("OUTPUT FORMAT (example item):\\n");
        sb.append("{\"id\":\"q1\",\"type\":\"SHORT_ANSWER\",\"question\":\"...\",\"expectedAnswer\":\"...\",\"answers\":[],\"difficulty\":3,\"tags\":[\"Indicator A\",\"Indicator B\"]}\\n");
        sb.append("Finally: RETURN ONLY the single JSON ARRAY (no surrounding text, no explanations, no extra whitespace outside the JSON).\\n");
        sb.append("\\nIndicators (use these verbatim as tags):\\n");
        sb.append(indicators);
        
        return sb.toString();
    }

    private String buildUserContent(PromptContext context) {
        StringBuilder sb = new StringBuilder();
        sb.append("Indicator:\n").append(context.getExistingIndicators()).append("\n\nResources:\n");
        
        List<String> resources = context.getResourceContents();
        int limit = Math.min(resources.size(), MAX_RESOURCES);
        
        for (int i = 0; i < limit; i++) {
            String text = resources.get(i);
            if (text == null) continue;
            
            if (text.length() > PER_RESOURCE_CHAR_LIMIT) {
                text = text.substring(0, PER_RESOURCE_CHAR_LIMIT) + " [TRUNCATED]";
            }
            sb.append(text).append("\n---\n");
        }
        
        return sb.toString();
    }
}

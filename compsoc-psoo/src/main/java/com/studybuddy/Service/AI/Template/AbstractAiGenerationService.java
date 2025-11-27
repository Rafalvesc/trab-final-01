package com.studybuddy.Service.AI.Template;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studybuddy.Exception.AiProviderException;
import com.studybuddy.Exception.ResourceProcessingException;
import com.studybuddy.Service.AI.AiChatClient;
import com.studybuddy.Service.AI.AiRequest;
import com.studybuddy.Service.AI.AiResponse;
import com.studybuddy.Service.AI.Strategy.PromptContext;
import com.studybuddy.Service.AI.Strategy.PromptStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public abstract class AbstractAiGenerationService<T> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final AiChatClient aiChatClient;

    protected AbstractAiGenerationService(AiChatClient aiChatClient) {
        this.aiChatClient = aiChatClient;
    }

    public final void generate(UUID studyflowId) {
        log.info("Starting AI generation for studyflow: {}", studyflowId);
        long startTime = System.currentTimeMillis();

        try {
            PromptContext context = gatherContext(studyflowId);

            PromptStrategy strategy = selectPromptStrategy(context);

            String promptContent = strategy.buildPrompt(context);
            log.info("=== PROMPT BUILT ===");
            log.info("Prompt length: {}", promptContent.length());
            log.info("Prompt (first 500 chars): {}", promptContent.substring(0, Math.min(500, promptContent.length())));

            AiRequest request = buildAiRequest(promptContent);

            AiResponse response = aiChatClient.sendRequest(request);

            log.info("=== AI RESPONSE RECEIVED ===");
            log.info("Success: {}", response.isSuccessful());
            log.info("Content length: {}", response.getContent() != null ? response.getContent().length() : 0);
            log.info("Content (first 1000 chars): {}", response.getContent() != null ? response.getContent().substring(0, Math.min(1000, response.getContent().length())) : "NULL");

            if (!response.isSuccessful()) {
                throw new AiProviderException("AI provider returned unsuccessful response");
            }

            JsonNode parsedContent = parseResponse(response.getContent());

            T processedData = mapResponseToData(parsedContent);

            persistResults(studyflowId, processedData);

            long duration = System.currentTimeMillis() - startTime;
            log.info("AI generation completed for studyflow: {} in {}ms", studyflowId, duration);

        } catch (AiProviderException | ResourceProcessingException e) {
            log.error("AI generation failed for studyflow: {}", studyflowId, e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during AI generation for studyflow: {}", studyflowId, e);
            throw new ResourceProcessingException("Unexpected error during AI generation", e);
        }
    }

    protected abstract PromptContext gatherContext(UUID studyflowId);

    protected abstract PromptStrategy selectPromptStrategy(PromptContext context);

    protected AiRequest buildAiRequest(String promptContent) {
        return AiRequest.builder()
                .addSystemMessage(getSystemPrompt())
                .addUserMessage(promptContent)
                .temperature(getTemperature())
                .maxTokens(getMaxTokens())
                .build();
    }

    protected JsonNode parseResponse(String content) {
        try {
            log.debug("Parsing AI response, length: {}", content.length());
            
            String cleanedContent = content.trim();
            
            int arrayStart = cleanedContent.indexOf('[');
            int arrayEnd = cleanedContent.lastIndexOf(']');
            
            if (arrayStart != -1 && arrayEnd != -1 && arrayEnd > arrayStart) {
                cleanedContent = cleanedContent.substring(arrayStart, arrayEnd + 1);
                log.debug("Extracted JSON array from position {} to {}", arrayStart, arrayEnd);
            }
            
            JsonNode parsed = objectMapper.readTree(cleanedContent);
            
            if (!parsed.isArray()) {
                log.warn("Model response is not a JSON array after extraction");
                throw new AiProviderException("Model did not return a JSON array");
            }
            
            log.debug("Successfully parsed JSON array with {} elements", parsed.size());
            return parsed;
            
        } catch (AiProviderException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to parse AI response as JSON", e);
            throw new AiProviderException("Failed to parse AI response", e);
        }
    }

    protected abstract T mapResponseToData(JsonNode parsedContent);

    protected abstract void persistResults(UUID studyflowId, T data);

    protected String getSystemPrompt() {
        return "You are an assistant that must output ONLY a single JSON ARRAY and nothing else.";
    }

    protected double getTemperature() {
        return 0.0;
    }

    protected int getMaxTokens() {
        return 2000;
    }
}

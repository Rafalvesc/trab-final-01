package com.studybuddy.Service.Studyflow.Indicator;

import com.fasterxml.jackson.databind.JsonNode;
import com.studybuddy.Entity.IndicatorEntity;
import com.studybuddy.Exception.EntityNotFoundException;
import com.studybuddy.Repository.Studyflow.Indicator.IndicatorEntityRepository;
import com.studybuddy.Repository.Studyflow.StudyflowEntityRepository;
import com.studybuddy.Service.AI.AiChatClient;
import com.studybuddy.Service.AI.Strategy.IndicatorFromResourcesPromptStrategy;
import com.studybuddy.Service.AI.Strategy.IndicatorFromTextPromptStrategy;
import com.studybuddy.Service.AI.Strategy.PromptContext;
import com.studybuddy.Service.AI.Strategy.PromptStrategy;
import com.studybuddy.Service.AI.Template.AbstractAiGenerationService;
import com.studybuddy.Service.Extractor.ExtractTextFromResources;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenerateIndicatorServiceImpl extends AbstractAiGenerationService<List<IndicatorEntity>> 
        implements GenerateIndicatorService {

    private final ExtractTextFromResources extractTextFromResources;
    private final StudyflowEntityRepository studyflowEntityRepository;
    private final IndicatorEntityRepository indicatorEntityRepository;
    private final IndicatorFromTextPromptStrategy textStrategy;
    private final IndicatorFromResourcesPromptStrategy resourceStrategy;

    public GenerateIndicatorServiceImpl(
            AiChatClient aiChatClient,
            ExtractTextFromResources extractTextFromResources,
            StudyflowEntityRepository studyflowEntityRepository,
            IndicatorEntityRepository indicatorEntityRepository,
            IndicatorFromTextPromptStrategy textStrategy,
            IndicatorFromResourcesPromptStrategy resourceStrategy) {
        super(aiChatClient);
        this.extractTextFromResources = extractTextFromResources;
        this.studyflowEntityRepository = studyflowEntityRepository;
        this.indicatorEntityRepository = indicatorEntityRepository;
        this.textStrategy = textStrategy;
        this.resourceStrategy = resourceStrategy;
    }

    @Override
    protected PromptContext gatherContext(UUID studyflowId) {
        log.debug("Gathering context for studyflow: {}", studyflowId);
        
        String indicatorText = extractTextFromResources.getIndicatorText(studyflowId);
        List<String> resourceContents = extractTextFromResources.getTextFromAllResources(studyflowId);
        
        return PromptContext.builder()
                .indicatorText(indicatorText)
                .resourceContents(resourceContents)
                .build();
    }

    @Override
    protected PromptStrategy selectPromptStrategy(PromptContext context) {
        if (context.hasIndicatorText()) {
            log.debug("Using indicator text strategy");
            return textStrategy;
        } else if (context.hasResources()) {
            log.debug("Using resources strategy");
            return resourceStrategy;
        } else {
            throw new IllegalStateException("No indicator text or resources available");
        }
    }

    @Override
    protected List<IndicatorEntity> mapResponseToData(JsonNode parsedContent) {
        log.debug("Mapping response to indicator entities");
        
        List<String> rawTags = extractTagsFromJson(parsedContent);
        Set<String> normalizedTags = normalizeTags(rawTags);
        
        return normalizedTags.stream()
                .map(this::createIndicatorEntity)
                .collect(Collectors.toList());
    }

    @Override
    protected void persistResults(UUID studyflowId, List<IndicatorEntity> indicators) {
        log.debug("Persisting {} indicators for studyflow: {}", indicators.size(), studyflowId);
        
        if (!studyflowEntityRepository.existsById(studyflowId)) {
            throw new EntityNotFoundException("Studyflow", studyflowId.toString());
        }
        
        indicators.forEach(indicator -> indicator.setStudyflowId(studyflowId));
        indicatorEntityRepository.saveAll(indicators);
        
        log.info("Successfully saved {} indicators for studyflow: {}", indicators.size(), studyflowId);
    }

    private List<String> extractTagsFromJson(JsonNode parsed) {
        List<String> rawTags = new ArrayList<>();
        
        for (JsonNode node : parsed) {
            if (node.isTextual()) {
                rawTags.add(node.asText());
            } else if (node.isObject()) {
                extractTagFromObject(node, rawTags);
            }
        }
        
        return rawTags;
    }

    private void extractTagFromObject(JsonNode node, List<String> rawTags) {
        if (node.has("tag")) {
            rawTags.add(node.get("tag").asText());
        } else if (node.has("topic")) {
            rawTags.add(node.get("topic").asText());
        } else if (node.has("indicator")) {
            rawTags.add(node.get("indicator").asText());
        } else {
            Iterator<Map.Entry<String, JsonNode>> it = node.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> field = it.next();
                if (field.getValue().isTextual()) {
                    rawTags.add(field.getValue().asText());
                    break;
                }
            }
        }
    }

    private Set<String> normalizeTags(List<String> rawTags) {
        LinkedHashSet<String> normalized = new LinkedHashSet<>();
        
        for (String tag : rawTags) {
            if (tag == null) continue;
            
            String processed = tag.trim().replaceAll("\\s+", " ");
            if (processed.isEmpty()) continue;
            
            if (processed.length() > 200) {
                processed = processed.substring(0, 200).trim() + "...";
            }
            
            processed = toTitleCase(processed);
            normalized.add(processed);
            
            if (normalized.size() >= 50) break;
        }
        
        return normalized;
    }

    private IndicatorEntity createIndicatorEntity(String tag) {
        IndicatorEntity entity = new IndicatorEntity();
        entity.setId(UUID.randomUUID());
        entity.setIndicatorTag(tag);
        entity.setCorrectCount(0);
        entity.setReviewCount(0);
        entity.setAnsweredCount(0);
        return entity;
    }

    private String toTitleCase(String input) {
        if (input == null || input.isBlank()) return input;
        
        String[] small = {"a", "e", "o", "de", "do", "da", "dos", "das", "and", "or", "of", "in", "on", "with", "to"};
        Set<String> smallSet = Arrays.stream(small).collect(Collectors.toSet());
        String[] parts = input.toLowerCase().split(" ");
        StringBuilder out = new StringBuilder();
        
        for (int i = 0; i < parts.length; i++) {
            String p = parts[i];
            if (i > 0 && smallSet.contains(p)) {
                out.append(p);
            } else {
                out.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1));
            }
            if (i < parts.length - 1) out.append(" ");
        }
        
        return out.toString();
    }
}

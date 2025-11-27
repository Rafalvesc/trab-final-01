package com.studybuddy.Service.AI.Strategy;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndicatorFromResourcesPromptStrategy implements PromptStrategy {
    private static final int PER_RESOURCE_CHAR_LIMIT = 4500;
    private static final int MAX_RESOURCES = 8;

    @Override
    public String buildPrompt(PromptContext context) {
        StringBuilder sb = new StringBuilder();
        sb.append("Você receberá vários textos de recursos abaixo. ");
        sb.append("Sua tarefa: extrair e sintetizar tópicos/tags de estudo significativos desses recursos. ");
        sb.append("RETORNE APENAS um único ARRAY JSON e NADA MAIS. ");
        sb.append("Cada item do array deve ser uma string (o tópico) ou um objeto com campos 'tag' e 'display' opcionais. ");
        sb.append("IMPORTANTE: Retorne NO MÁXIMO 15 tópicos, concisos (<=120 caracteres), sem duplicatas, ordenados por importância/cobertura. ");
        sb.append("Todos os tópicos devem estar em português brasileiro. ");
        sb.append("\n\nRecursos:\n");

        List<String> resources = context.getResourceContents();
        int limit = Math.min(resources.size(), MAX_RESOURCES);
        
        for (int i = 0; i < limit; i++) {
            String text = resources.get(i);
            if (text == null) continue;
            
            if (text.length() > PER_RESOURCE_CHAR_LIMIT) {
                text = text.substring(0, PER_RESOURCE_CHAR_LIMIT) + " [TRUNCADO]";
            }
            sb.append(text).append("\n---\n");
        }
        
        return sb.toString();
    }
}

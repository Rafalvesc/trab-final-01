package com.studybuddy.Service.AI.Strategy;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OverviewFromResourcesPromptStrategy implements PromptStrategy {
    private static final int PER_RESOURCE_CHAR_LIMIT = 4500;
    private static final int MAX_RESOURCES = 8;
    private static final int MAX_SECTIONS = 6;
    private static final int MAX_WORDS_APPROX = 1600;

    @Override
    public String buildPrompt(PromptContext context) {
        StringBuilder sb = new StringBuilder();

        sb.append("Você receberá vários textos de recursos abaixo. ")
                .append("Sua tarefa: produzir UMA VISÃO GERAL (overview) bem estruturada e legível desses recursos. ")
                .append("**RETORNE APENAS O CONTEÚDO EM FORMATO MARKDOWN E NADA MAIS.** ")
                .append("Não inclua explicações sobre como foi gerado, metadados, JSON ou quaisquer instruções. ")
                .append("Use português brasileiro.\n\n");

        sb.append("Regras de formato (obrigatórias):\n");
        sb.append("- Retorne um documento em **Markdown** pronto para conversão para HTML/PDF.\n");
        sb.append("- Use um título principal (`# Título`) seguido por até ")
                .append(MAX_SECTIONS)
                .append(" seções (`##`) e subtítulos (`###`) conforme necessário.\n");
        sb.append("- Inclua pelo menos as seções: `Resumo` (um parágrafo conciso) e `Principais Conceitos` (lista com bullets curtos).\n");
        sb.append("- Seja conciso: aprox. máximo de ")
                .append(MAX_WORDS_APPROX)
                .append(" palavras no total; prefira clareza à verbosidade.\n");
        sb.append("- Use parágrafos curtos (2-4 linhas) e listas curtas quando úteis.\n");
        sb.append("- Não repita conteúdo; sintetize e remova redundâncias.\n");
        sb.append("- Mantenha linguagem técnica quando apropriado, mas acessível para estudantes.\n\n");

        if (context.hasIndicatorText()) {
            sb.append("Contexto/indicador fornecido (usar para guiar o foco do overview):\n");
            sb.append("```\n").append(context.getIndicatorText()).append("\n```\n\n");
        }

        sb.append("Recursos (use o conteúdo abaixo para compor o overview):\n\n");

        List<String> resources = context.getResourceContents();
        int limit = resources == null ? 0 : Math.min(resources.size(), MAX_RESOURCES);

        for (int i = 0; i < limit; i++) {
            String text = resources.get(i);
            if (text == null) continue;

            if (text.length() > PER_RESOURCE_CHAR_LIMIT) {
                text = text.substring(0, PER_RESOURCE_CHAR_LIMIT) + " [TRUNCADO]";
            }

            sb.append("### Recurso ").append(i + 1).append("\n");
            sb.append("```\n");
            sb.append(text).append("\n");
            sb.append("```\n\n");
        }

        sb.append("Instruções finais (muito importantes):\n");
        sb.append("- **RETORNE SOMENTE** o Markdown do overview. Nada além disso.\n");
        sb.append("- O Markdown será convertido para PDF; use títulos e estrutura, evite HTML bruto.\n");
        sb.append("- Se houver incerteza, prefira resumir o conteúdo principal e indicar lacunas de informação dentro do texto (breve).\n");

        return sb.toString();
    }
}

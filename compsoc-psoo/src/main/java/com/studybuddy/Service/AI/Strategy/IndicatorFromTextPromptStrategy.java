package com.studybuddy.Service.AI.Strategy;

import org.springframework.stereotype.Component;

@Component
public class IndicatorFromTextPromptStrategy implements PromptStrategy {
    
    @Override
    public String buildPrompt(PromptContext context) {
        StringBuilder sb = new StringBuilder();
        sb.append("Você receberá o conteúdo de um arquivo 'indicador' abaixo. ");
        sb.append("Sua tarefa: dividir o conteúdo em tópicos de estudo significativos. ");
        sb.append("Ignore partes do indicador que não representam um tópico (como títulos, nomes de professores, etc). ");
        sb.append("RETORNE APENAS um único ARRAY JSON e NADA MAIS. ");
        sb.append("Cada item do array deve ser uma string (o tópico) ou um objeto com campos 'tag' e 'display' opcionais. ");
        sb.append("Normalize espaços em branco, remova duplicatas, prefira títulos concisos (<=120 caracteres). ");
        sb.append("IMPORTANTE: Retorne NO MÁXIMO 15 tópicos. ");
        sb.append("Todos os tópicos devem estar em português brasileiro. ");
        sb.append("Se o indicador já listar tópicos, limpe-os e retorne-os como itens separados. ");
        sb.append("Antes de enviar, releia os arquivos e compare com seu JSON. Você deixou algum tópico significativo faltando? Você duplicou algum conteúdo? ");
        sb.append("Reflita sobre seu JSON e envie apenas a cópia final perfeita com NO MÁXIMO 15 tópicos.");
        sb.append("\n\nIndicador:\n");
        sb.append(context.getIndicatorText());
        
        return sb.toString();
    }
}

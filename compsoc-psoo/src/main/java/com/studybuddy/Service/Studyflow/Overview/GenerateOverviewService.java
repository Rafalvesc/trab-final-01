package com.studybuddy.Service.Studyflow.Overview;

import java.util.UUID;

public interface GenerateOverviewService {
    /**
     * Dispara o pipeline de geração (pode chamar a AI se necessário).
     * Preservado para compatibilidade — mantém o contrato simples.
     */
    void generate(UUID studyflowId);

    /**
     * Gera (se precisar) e retorna o PDF correspondente ao overview do studyflow.
     * Implementação fará check para evitar re-chamadas desnecessárias ao LLM.
     *
     * @param studyflowId id do studyflow
     * @return bytes do PDF (application/pdf)
     */
    byte[] returnPdf(UUID studyflowId);
}

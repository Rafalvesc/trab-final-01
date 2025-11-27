package com.studybuddy.Service.Studyflow.Question;

import com.studybuddy.Dto.Studyflow.Question.QuestionPostDto;
import com.studybuddy.Entity.IndicatorEntity;
import com.studybuddy.Entity.QuestionEntity;
import com.studybuddy.Exception.GenericException;
import com.studybuddy.Repository.Studyflow.Indicator.IndicatorEntityRepository;
import com.studybuddy.Repository.Studyflow.QuestionEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostAnswerServiceImpl implements PostAnswerService {
    private final IndicatorEntityRepository indicatorEntityRepository;
    QuestionEntityRepository questionEntityRepository;
    public PostAnswerServiceImpl(QuestionEntityRepository questionEntityRepository, IndicatorEntityRepository indicatorEntityRepository) {
        this.questionEntityRepository = questionEntityRepository;
        this.indicatorEntityRepository = indicatorEntityRepository;
    }
    @Override
    @Transactional
    public void postAnswer(QuestionPostDto questionPostDto) {
        Optional<QuestionEntity> questionEntityOptional = questionEntityRepository.findById(questionPostDto.getId());
        if (questionEntityOptional.isEmpty()) {
            throw new GenericException("Entidade nao encontrada");
        }
        QuestionEntity questionEntity = questionEntityOptional.get();
        questionEntity.setUserAnswer(questionPostDto.getUserAnswer());
        questionEntity.setAnswered(true);
        questionEntity.setLastReviewedAt(Instant.now());
        questionEntity.setReviewCount(questionEntity.getReviewCount() + 1);
        questionEntity.setCorrect(questionPostDto.isCorrect());
        questionEntityRepository.save(questionEntity);

        updateIndicatorCounters(questionEntity.getStudyFlow().getId(), questionEntity.getTags(), questionEntity.getCorrect());
    }


    private void updateIndicatorCounters(UUID id, String tags, Boolean correct) {
        List<String> tagsSplit = List.of(tags.split(","));
        List<IndicatorEntity> indicatorEntityList = indicatorEntityRepository.findAllByStudyflowId(id);

        if (indicatorEntityList.isEmpty()) {
            throw new GenericException("Nenhum indicador encontrado");
        }

        indicatorEntityList.forEach(indicator -> {
            boolean matches = tagsSplit.stream().anyMatch(tag -> tag.equals(indicator.getIndicatorTag()));

            if (matches) {
                indicator.setAnsweredCount(indicator.getAnsweredCount() + 1);
                if (correct){
                    indicator.setCorrectCount(indicator.getCorrectCount() + 1);
                }
            }
        });

    }
}

package com.studybuddy.Service.Studyflow.Question;

import com.studybuddy.Dto.Studyflow.Question.QuestionGetDto;
import com.studybuddy.Entity.QuestionEntity;
import com.studybuddy.Repository.Studyflow.QuestionEntityRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetQuestionsByStudyflowIdImpl implements GetQuestionsByStudyflowId {
    private final QuestionEntityRepository questionEntityRepository;
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

    public GetQuestionsByStudyflowIdImpl(QuestionEntityRepository questionEntityRepository) {
        this.questionEntityRepository = questionEntityRepository;
    }

    @Override
    public List<QuestionGetDto> get(UUID studyflowId) {
        log.info("ENTER get(), studyflowId={}", studyflowId);
        List<QuestionEntity> questionEntityList = null;
        try{
            questionEntityList = questionEntityRepository.findAllByStudyFlowId(studyflowId);
        } catch (Exception e){
            e.printStackTrace();
        }
        log.info("Repository returned list reference={}, size={}", questionEntityList, questionEntityList != null ? questionEntityList.size() : "null");

        List<QuestionGetDto> dtoList = new ArrayList<>();
        for (QuestionEntity q : questionEntityList) {
            log.debug("Mapping id={}, studyFlow={}", q.getId(), q.getStudyFlow());
            QuestionGetDto dto = new QuestionGetDto();
            dto.setId(q.getId());
            dto.setQuestion(q.getQuestion());
            dto.setCorrect(q.getCorrect());
            dto.setAnswers(q.getAnswers());
            dto.setExpectedAnswer(q.getExpectedAnswer());
            dto.setAnswered(q.isAnswered());
            dto.setStudyflowId(q.getStudyFlow() != null ? q.getStudyFlow().getId() : null);
            dto.setLastReviewedAt(q.getLastReviewedAt() != null ? q.getLastReviewedAt() : Instant.now());
            dto.setTags(q.getTags());
            dto.setType(q.getType());
            dtoList.add(dto);
        }

        log.info("EXIT get() -> returning {} dtos", dtoList.size());
        return dtoList;
    }
}

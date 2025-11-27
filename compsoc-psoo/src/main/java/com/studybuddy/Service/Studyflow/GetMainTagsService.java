package com.studybuddy.Service.Studyflow;

import com.studybuddy.Entity.IndicatorEntity;
import com.studybuddy.Exception.GenericException;
import com.studybuddy.Repository.Studyflow.Indicator.IndicatorEntityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetMainTagsService {
    private final IndicatorEntityRepository indicatorEntityRepository;

    public GetMainTagsService(IndicatorEntityRepository indicatorEntityRepository) {
        this.indicatorEntityRepository = indicatorEntityRepository;
    }

    List<String> getTags(UUID studyflowId){
        List<IndicatorEntity> indicatorEntityList = indicatorEntityRepository.findAllByStudyflowId(studyflowId);
        if (indicatorEntityList.isEmpty()){
            throw new GenericException("erro");
        }
        List<String> tags = new ArrayList<>();
        for (int i = 0; i<5; i++){
            tags.add(indicatorEntityList.get(i).getIndicatorTag());
        }
        return tags;
    }

}

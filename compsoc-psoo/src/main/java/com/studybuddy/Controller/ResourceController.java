package com.studybuddy.Controller;

import com.studybuddy.Dto.Studyflow.ResourceDto;
import com.studybuddy.Service.Studyflow.Resource.PostResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/studyflow/resources")
@CrossOrigin(origins = "http://localhost:4200")
public class ResourceController {
    private final PostResourceService postResourceService;

    public ResourceController(PostResourceService postResourceService) {
        this.postResourceService = postResourceService;
    }

    @PostMapping
    public ResponseEntity<String> post(@RequestBody List<ResourceDto> resourceDtoList) {
        try {
            postResourceService.createBatch(resourceDtoList);
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }
}

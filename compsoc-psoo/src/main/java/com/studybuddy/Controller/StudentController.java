package com.studybuddy.Controller;

import com.studybuddy.Dto.Student.LoginDto;
import com.studybuddy.Dto.Student.RegisterDto;
import com.studybuddy.Dto.Student.StudentPatchDto;
import com.studybuddy.Service.User.RegisterService;
import com.studybuddy.Service.Student.PatchStudentService;
import com.studybuddy.Service.User.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
    private final RegisterService registerService;
    private final LoginService loginService;
    private final PatchStudentService patchStudentService;

    public StudentController(RegisterService registerService,
                             LoginService loginService, PatchStudentService patchStudentService) {
        this.registerService = registerService;
        this.loginService = loginService;
        this.patchStudentService = patchStudentService;
    }
    @PostMapping
    public ResponseEntity<String> postStudent(@RequestBody RegisterDto registerDto){
        try {
            registerService.create(registerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("criado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UUID> login(@RequestBody LoginDto loginDto){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(loginService.login(loginDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PatchMapping
    public ResponseEntity<String> patchStudent(@RequestBody StudentPatchDto studentPatchDto){
        try {
            patchStudentService.patch(studentPatchDto);
            return ResponseEntity.status(HttpStatus.OK).body("patched");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("erro");

        }
    }


}

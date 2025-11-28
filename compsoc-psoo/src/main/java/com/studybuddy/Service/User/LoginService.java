package com.studybuddy.Service.User;

import com.studybuddy.Dto.Student.LoginDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface LoginService {
    UUID login(@RequestBody LoginDto loginDto);
}

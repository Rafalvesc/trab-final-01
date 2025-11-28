package com.studybuddy.Service.User;

import com.studybuddy.Dto.Student.LoginDto;
import com.studybuddy.Entity.StudentEntity;
import com.studybuddy.Entity.UserEntity;
import com.studybuddy.Exception.GenericException;
import com.studybuddy.Repository.Student.StudentEntityRepository;
import com.studybuddy.Repository.User.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

//sim nao tem criptografia nem nada, depois se quiserem colocar um password encoder + JWT mas
//agr é só faz de conta
@Service
public class LoginServiceImpl implements LoginService{
    private final UserEntityRepository userEntityRepository;
    private final StudentEntityRepository studentEntityRepository;

    public LoginServiceImpl(UserEntityRepository userEntityRepository, StudentEntityRepository studentEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.studentEntityRepository = studentEntityRepository;
    }

    @Override
    public UUID login(LoginDto loginDto) {
        Optional<UserEntity> proposedLogin = userEntityRepository.findByEmail(loginDto.getEmail());
        if(proposedLogin.isEmpty()){
            throw new GenericException("entidade user n encontrada no login");
        }

        if (!proposedLogin.get().getPasswordHash().equals(loginDto.getPassword())){
            throw new GenericException("as senhas nao batem");
        }
        Optional<StudentEntity> studentEntityOptional = studentEntityRepository.findByUserEntity(proposedLogin.get());

        if(studentEntityOptional.isEmpty()){
            throw new GenericException("erro impossível");
        }

        return studentEntityOptional.get().getId();
    }
}

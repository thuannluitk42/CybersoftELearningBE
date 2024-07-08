package com.thuannluit.quizzes.service.impl;

import com.thuannluit.quizzes.entity.User;
import com.thuannluit.quizzes.repository.UserRepository;
import com.thuannluit.quizzes.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {

    @Autowired
    UserRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean checkLogin(String username, String password) {
        User user = usersRepository.findByUsername(username);
        if(null == user) {
            return false;
        } else {
            return passwordEncoder.matches(password, user.getPassword());
        }
    }
}

package com.thuannluit.quizzes.service.impl;

import com.thuannluit.quizzes.entity.Role;
import com.thuannluit.quizzes.entity.User;
import com.thuannluit.quizzes.payload.DataResponse;
import com.thuannluit.quizzes.repository.RoleRepository;
import com.thuannluit.quizzes.repository.UserRepository;
import com.thuannluit.quizzes.service.IUserService;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public DataResponse saveUser(String username, String password) throws BadRequestException {
        Set<Role> setRolesRequest = new HashSet<>();

            User u = new User();

            Role roles = rolesRepository.findByRole_name("ROLE_USER");
            System.out.println(roles.getRole_name());
            setRolesRequest.add(roles);

            u.setUsername(username);
            u.setPassword(passwordEncoder.encode(password));
            Date date = new Date();
            u.setCreated_at(date);

            u.setRoles(setRolesRequest);
            userRepository.save(u);

        return DataResponse.ok("");
    }
}

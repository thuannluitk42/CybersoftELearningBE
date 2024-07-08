package com.thuannluit.quizzes.service;

import com.thuannluit.quizzes.payload.DataResponse;
import org.apache.coyote.BadRequestException;

public interface IUserService {

    DataResponse saveUser(String username, String password) throws BadRequestException;
}

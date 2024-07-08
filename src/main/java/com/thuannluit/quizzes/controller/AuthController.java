package com.thuannluit.quizzes.controller;

import com.thuannluit.quizzes.entity.User;
import com.thuannluit.quizzes.payload.AuthResponse;
import com.thuannluit.quizzes.payload.LoginDto;
import com.thuannluit.quizzes.payload.SignUpDto;
import com.thuannluit.quizzes.repository.UserRepository;
import com.thuannluit.quizzes.service.ILoginService;
import com.thuannluit.quizzes.service.IUserService;
import com.thuannluit.quizzes.utils.JwtUtilsHelper;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.crypto.SecretKey;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    JwtUtilsHelper jwtUtilsHelper;

    @Operation(summary = "Authenticate a user and generate a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid username or password", content = @Content)
    })
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        System.out.println("authenticateUser:" + loginDto.getUsername() + "-------" + loginDto.getPassword());

        AuthResponse authResponse = new AuthResponse();
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String encrypted = Encoders.BASE64.encode(secretKey.getEncoded());
        System.out.println(encrypted);
        if (loginService.checkLogin(loginDto.getUsername(), loginDto.getPassword())) {
            String token = jwtUtilsHelper.generateToken(loginDto.getUsername());
            authResponse.setMessage("Login success");
            authResponse.setJwt(token);
            authResponse.setStatus(true);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } else {
            authResponse.setMessage("User not found");
            authResponse.setStatus(false);
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Username is already taken", content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody SignUpDto signUpDto) throws BadRequestException {
        System.out.println("registerUser:" + signUpDto.getUsername() + "-------" + signUpDto.getPassword());

        AuthResponse authResponse = new AuthResponse();
        User u = userRepository.findByUsername(signUpDto.getUsername());

        if (null != u) {
            authResponse.setJwt("");
            authResponse.setMessage("Username is already taken!");
            authResponse.setStatus(true);
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        } else {
            userService.saveUser(signUpDto.getUsername(), signUpDto.getPassword());
            authResponse.setJwt("");
            authResponse.setMessage("Register Success");
            authResponse.setStatus(true);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }
    }
}

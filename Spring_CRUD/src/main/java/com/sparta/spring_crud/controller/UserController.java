package com.sparta.spring_crud.controller;

import com.sparta.spring_crud.dto.ResponseDto;
import com.sparta.spring_crud.dto.LoginRequestDto;
import com.sparta.spring_crud.dto.SignupRequestDto;
import com.sparta.spring_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService; // Userservice 기능 사용 위해 의존성 주입

    // 회원가입 기능
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok().body(userService.signup(signupRequestDto));
    }

    // 로그인 기능
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response) {
        return ResponseEntity.ok().body(userService.login(loginRequestDto, response));
    }

}

package com.sparta.spring_crud.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestDto {

    @NotNull(message = "아이디는 필수 값입니다.") // 공백일 시에 메세지 반환
    @Size(min = 4, max = 10, message = "4자 ~ 10자 사이로 입력해주세요.")
    @Pattern(regexp = "[a-z0-9]*$", message = "알파벳 소문자(a~z)와 숫자(0~9)만 사용 가능합니다.")
    private String username;

    @NotNull(message = "비밀번호는 필수 값입니다.") // 공백일 시에 메세지 반환
    @Size(min = 8, max = 15, message = "8자 ~ 15자 사이로 입력해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()-_=+]*$", message = "알파벳 대소문자(a~z, A~Z)와 숫자(0~9), 특수문자만 사용 가능합니다.")
    private String password;
    private boolean admin = true;
    private String adminToken = "";
    private String adminkey = "";
}

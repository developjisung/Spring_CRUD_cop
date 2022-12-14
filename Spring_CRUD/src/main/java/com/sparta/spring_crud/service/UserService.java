package com.sparta.spring_crud.service;

import com.sparta.spring_crud.dto.ResponseDto;
import com.sparta.spring_crud.dto.LoginRequestDto;
import com.sparta.spring_crud.dto.SignupRequestDto;
import com.sparta.spring_crud.dto.StatusEnum;
import com.sparta.spring_crud.entity.User;
import com.sparta.spring_crud.entity.UserRoleEnum;
import com.sparta.spring_crud.jwt.JwtUtil;
import com.sparta.spring_crud.repository.UserRepository;
import com.sparta.spring_crud.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.sparta.spring_crud.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String adminkey = signupRequestDto.getAdminkey();

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new CustomException(DUPLICATED_USERNAME);
        }
        // 회원 중복 확인

        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminkey().equals(ADMIN_TOKEN)) {
                role = UserRoleEnum.USER;
            } else {
                role = UserRoleEnum.ADMIN;
            }
            // 사용자 ROLE 확인
        }
        User user = new User(username, password, role);
        // username, password, role 값을 가진 user 객체 생성
        userRepository.save(user);
        // 유저 DB에 user 객체 값 저장
        return new ResponseDto(StatusEnum.OK);
    }

    @Transactional(readOnly = true)
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername(); // loginRequestDto에서 얻은 유저 이름을 username에 저장
        String password = loginRequestDto.getPassword(); // loginRequsetDto에서 얻은 패스워드를 password에 저장

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        // user 객체에 유저 DB에서 유저 이름으로 얻은 값을 저장 -> 아니면 예외 처리

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(NOT_FOUND_USER);
        }
        // password의 값과 user에서 얻은 password의 값이 다르다면 예외 처리 문구
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        //response 객채에 얻은 데이터들을 담아서 반환
        return new ResponseDto(StatusEnum.OK);
    }

}

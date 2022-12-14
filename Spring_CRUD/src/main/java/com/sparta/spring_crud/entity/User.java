package com.sparta.spring_crud.entity;

import com.sparta.spring_crud.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) // index가 아닌 String 값을 저장하기 위한 코드
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role) {
        this.username = username; // 입력받은 username 데이터 저장
        this.password = password; // 입력받은 password 데이터 저장
        this.role = role; // 입력받은 role 데이터 저장
    }
}
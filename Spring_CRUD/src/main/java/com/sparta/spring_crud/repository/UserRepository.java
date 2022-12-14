package com.sparta.spring_crud.repository;

import com.sparta.spring_crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // 유저 DB에서 유저 이름이 같은 데이터를 User 객체에 저장하는 메소드
}
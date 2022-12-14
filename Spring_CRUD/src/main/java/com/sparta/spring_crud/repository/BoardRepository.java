package com.sparta.spring_crud.repository;

import com.sparta.spring_crud.entity.Board;
import com.sparta.spring_crud.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreatedAtDesc(); // 생성 시간 기준으로 모든 요청들을 리스트로 담는 메소드
    Optional<Board> findByIdAndUserId(Long id, Long UserId); // 게시판 ID와 유저 ID로 모든 데이터를 담는 메소드
}
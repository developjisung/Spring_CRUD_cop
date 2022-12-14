package com.sparta.spring_crud.repository;

import com.sparta.spring_crud.entity.Board;
import com.sparta.spring_crud.entity.BoardLike;
import com.sparta.spring_crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardAndUser(Board board, User user); // board와 user 객체 데이터로 DB에 접근해서 같은 데이터를 리스트형으로 담는 메소드
    @Transactional
    void deleteByBoardAndUser(Board board, User user); // board와 user 객체 데이터로 DB에 접근해서 같은 데이터를 삭제하는 메소드
}
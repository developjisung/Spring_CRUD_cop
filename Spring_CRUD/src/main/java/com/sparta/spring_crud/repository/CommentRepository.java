package com.sparta.spring_crud.repository;

import com.sparta.spring_crud.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long commentId, Long UserId); // 댓글 ID와 유저 ID로 댓글 DB에서 얻은 값을 Comment 객체에 담는 메소드
}
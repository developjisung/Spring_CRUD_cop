package com.sparta.spring_crud.repository;

import com.sparta.spring_crud.entity.Comment;
import com.sparta.spring_crud.entity.CommentLike;
import com.sparta.spring_crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByCommentAndUser(Comment comment, User user); // comment와 user 객체 값으로 찾은 모든 데이터를 리스트형으로 CommentLike 객체에 저장하는 메소드
    @Transactional
    void deleteByCommentAndUser(Comment comment, User user); // comment와 user 객체 데이터와 일치하는 데이터를 삭제하는 메소드
}
package com.sparta.spring_crud.dto;

import com.sparta.spring_crud.entity.Comment;
import com.sparta.spring_crud.entity.CommentLike;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.boot.web.server.Cookie;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentDto {
    private Long id;
    private String username;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int likeCnt;

    public CommentDto(Comment comment) {
        this.id = comment.getId(); // comment 객채에서 ID값 저장
        this.username = comment.getUsername(); // comment 객채에서 유저 이름 저장
        this.comment = comment.getComment(); // comment 객채에서 댓글 저장
        this.likeCnt = comment.getLikeCnt(); // comment 객채에서 좋아요 갯수 저장
        this.createdAt = comment.getCreatedAt(); // comment 객채에서 만들어진 시간 저장
        this.modifiedAt = comment.getModifiedAt(); // comment 객채에서 수정된 시간 저장
    }
}

package com.sparta.spring_crud.dto;

import com.sparta.spring_crud.entity.Comment;
import com.sparta.spring_crud.entity.User;
import lombok.Getter;

@Getter
public class CommentLikeDto {
    private Long id;
    private Comment comment;
    private User user;

    public CommentLikeDto(Long id, Comment comment, User user) {
        this.id = id;
        this.comment = comment;
        this.user = user;
    }
}
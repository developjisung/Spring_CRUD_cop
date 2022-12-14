package com.sparta.spring_crud.dto;

import com.sparta.spring_crud.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto extends ResponseDto {
    CommentDto comment;
    public CommentResponseDto(StatusEnum status, Comment comment) {
        super(status);
        this.comment = new CommentDto(comment);
    }
}

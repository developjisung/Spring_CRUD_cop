package com.sparta.spring_crud.dto;

import com.sparta.spring_crud.entity.Board;
import lombok.Getter;

import java.util.List;

@Getter
public class BoardCommentResponseDto extends ResponseDto {

    private BoardDto board;

    public BoardCommentResponseDto(StatusEnum status, Board board, List<CommentDto> commentList) {
        super(status);
        this.board = new BoardDto(board, commentList);
    }
}
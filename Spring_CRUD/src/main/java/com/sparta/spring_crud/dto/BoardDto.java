package com.sparta.spring_crud.dto;

import com.sparta.spring_crud.entity.Board;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardDto {
    private Long id;
    private String title;
    private String username;
    private String content;
    private int likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<CommentDto> commentList = new ArrayList<>();

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.content = board.getContent();
        this.likeCnt = board.getLikeCnt();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }

    public BoardDto(Board board, List<CommentDto> commentList) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.content = board.getContent();
        this.likeCnt = board.getLikeCnt();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.commentList = commentList;
    }

}

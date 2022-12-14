package com.sparta.spring_crud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.spring_crud.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Board extends com.sparta.crud.entity.Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @OrderBy("createdAt DESC")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id desc")
    private List<com.sparta.crud.entity.BoardLike> boardLikes = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    @Column
    private int likeCnt;

    public Board(BoardRequestDto requestDto, String username, User user) {
        this.title = requestDto.getTitle(); // requestDto에서 얻은 타이틀 데이터 저장
        this.content = requestDto.getContent(); // requestDto에서 얻은 컨텐츠 데이터 저장
        this.username = username; // 입력된 username 데이터를 저장
        this.user = user; // user 객체 데이터 저장
        this.boardLikes = getBoardLikes(); // 필드에서 게시글 좋아요 데이터 저장
        this.likeCnt = getLikeCnt(); // 필드에서 게시글 좋아요 갯수 데이터 저장
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle(); // requestDto에서 받은 타이틀 값을 저장
        this.content = requestDto.getContent(); // requestDto에서 받은 컨텐츠 값을 저장
    }

    public void update_Cnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }
}
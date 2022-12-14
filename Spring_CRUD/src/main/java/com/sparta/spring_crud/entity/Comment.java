package com.sparta.spring_crud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.spring_crud.dto.CommentRequestDto;
import com.sparta.spring_crud.entity.CommentLike;
import com.sparta.spring_crud.entity.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "comment", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id desc")
    private List<CommentLike> commentLikes;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String comment;

    @Column
    private int likeCnt;

    public Comment(CommentRequestDto commentRequestDto, Board board, User user) {
        this.comment = commentRequestDto.getComment(); // commentRequestDto에서 댓글 데이터 저장
        this.username = user.getUsername(); // commentRequestDto에서 유저 이름 데이터 저장
        this.board = board; // board 객체
        this.user = user; // user 객체
        this.commentLikes = getCommentLikes(); // comment 객체 내에서 댓글 좋아요 데이터 저장
        this.likeCnt = getLikeCnt(); // comment 객체 내에서 댓글 좋아요 카운트 데이터 저장
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment(); // commentRequestDto의 comment 데이터를 comment 필드에 업데이트
    }

    public void update_Cnt(int likeCnt) {
        this.likeCnt = likeCnt; // likeCnt 필드값 업데이트
    }
}
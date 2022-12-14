package com.sparta.spring_crud.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class BoardLike extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    public BoardLike(Board board , User user){
        this.board = board; // 입력받은 board 객체 데이터를 저장
        this.user = user; // 입력받은 user 객체 데이터를 저장
    }
}
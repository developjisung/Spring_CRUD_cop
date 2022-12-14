package com.sparta.spring_crud.dto;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardListResponseDto extends ResponseDto {

    List<BoardDto> boradList = new ArrayList<>();

    public BoardListResponseDto(StatusEnum status) {
        super(status);
    }

    public void addBoard(BoardDto boardToDto) {
        boradList.add(boardToDto);
    }
}

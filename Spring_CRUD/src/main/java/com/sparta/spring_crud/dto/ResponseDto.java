package com.sparta.spring_crud.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private int statusCode;
    private String msg;


    public ResponseDto(StatusEnum status) {
        this.statusCode = status.statusCode;
        this.msg = status.msg;
    }

    public ResponseDto(String msg, int statusCode) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}

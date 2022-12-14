package com.sparta.spring_crud.controller;

import com.sparta.spring_crud.dto.*;
import com.sparta.spring_crud.security.UserDetailsImpl;
import com.sparta.spring_crud.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 포스트 저장
    @PostMapping("/save/posts")
    public ResponseEntity<BoardDto> createBorad(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(boardService.createBoard(requestDto, userDetails.getUser()));
    }

    // 포스트 전체 조회
    @GetMapping("/get/posts")
    public ResponseEntity<ResponseDto> getBoardList() {
        return ResponseEntity.ok().body(boardService.getBoardList());
    }

    // 포스트 상세 페이지
    @GetMapping("/get/post")
    public ResponseEntity<ResponseDto> getBoard(@RequestParam Long id) {
        return ResponseEntity.ok().body(boardService.getBoard(id));
    }

    // 포스트 수정
    @PutMapping("/update/post/{id}")
    public ResponseEntity<ResponseDto> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(boardService.update(id, requestDto, userDetails.getUser()));
    }

    // 포스트 삭제
    @DeleteMapping("delete/post/{id}")
    public ResponseEntity<ResponseDto> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(boardService.deleteBoard(id, userDetails.getUser()));
    }

    // 포스트 좋아요
    @PostMapping("save/post/like/{id}")
    public ResponseEntity<ResponseDto> addlike(
            @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.addlike(id, userDetails.getUser()));
    }

    // 포스트 좋아요 취소
    @DeleteMapping("delete/post/like/{id}")
    public ResponseEntity<ResponseDto> deletelike(
            @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(boardService.deletelike(id, userDetails.getUser()));
    }
}

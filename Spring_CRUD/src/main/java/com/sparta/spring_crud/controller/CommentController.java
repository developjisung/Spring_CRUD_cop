package com.sparta.spring_crud.controller;

import com.sparta.spring_crud.dto.ResponseDto;
import com.sparta.spring_crud.dto.CommentRequestDto;
import com.sparta.spring_crud.entity.User;
import com.sparta.spring_crud.security.UserDetailsImpl;
import com.sparta.spring_crud.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService; // CommentService 기능 사용을 위하여 의존성 주입


    // 댓글 달기
    @PostMapping("/save/comment/{id}")
    public ResponseEntity<ResponseDto> addComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.addComment(id, commentRequestDto, userDetails.getUser()));
    }

    // 댓글 수정
    @PutMapping("update/comment/{boardId}/{commentId}")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.updateComment(boardId, commentId, commentRequestDto, userDetails.getUser()));
    }

    // 댓글 삭제
    @DeleteMapping("delete/comment/{boardId}/{commentId}")
    public ResponseEntity<ResponseDto> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.deleteComment(boardId, commentId, userDetails.getUser()));
    }

    // 댓글 좋아요
    @PostMapping("save/comment/like/{id}")
    public ResponseEntity<ResponseDto> addlike(
            @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.addlike(id, userDetails.getUser()));
    }

    // 댓글 삭제
    @DeleteMapping("delete/comment/like/{id}")
    public ResponseEntity<ResponseDto> deletelike(
            @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.deletelike(id, userDetails.getUser()));
    }
}

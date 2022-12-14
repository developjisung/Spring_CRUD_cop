package com.sparta.spring_crud.service;

import com.sparta.spring_crud.dto.*;
import com.sparta.spring_crud.entity.*;
import com.sparta.spring_crud.repository.BoardRepository;
import com.sparta.spring_crud.repository.CommentLikeRepository;
import com.sparta.spring_crud.repository.CommentRepository;
import com.sparta.spring_crud.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sparta.spring_crud.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final CommentLikeRepository commentLikeRepository;
    // 객체 사용을 위한 의존성 주입

    @Transactional
    public ResponseDto addComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );
        // 전달받은 게시글 ID로 게시글 DB에 일치하는 것이 있는지 확인

        Comment comment = commentRepository.save(new Comment(commentRequestDto, board, user));
        // 요청 및 게시판, 유저 데이터를 Comment 객체에 담아서 DB에 저장

        return new CommentResponseDto(StatusEnum.OK, comment);
    }

    @Transactional
    public ResponseDto updateComment(Long boardId, Long commentId, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );
        // board 객체에 게시판 ID로 DB에서 가져온 데이터를 저장
        UserRoleEnum userRoleEnum = user.getRole();

        Comment comment;

        if (userRoleEnum == UserRoleEnum.ADMIN) {
            comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new CustomException(NOT_FOUND_COMMENT)
            );
            // 유저의 권한이 ADMIN이라면 comment 객체에 댓글 ID로 찾은 데이터를 저장 -> 아니면 예외 처리 문구
        } else {
            comment = commentRepository.findByIdAndUserId(commentId, user.getId()).orElseThrow(
                    () -> new CustomException(AUTHORIZATION)
            );
            // 유저의 권한이 ADMIN이 아니라면 comment 객체에 댓글 ID랑 유저 ID로 찾아서 일치하는 데이터를 저장 -> 아니면 예외 처리
        }
        comment.update(commentRequestDto);

        return new CommentResponseDto(StatusEnum.OK, comment);
    }

    @Transactional
    public ResponseDto deleteComment(Long boardId, Long commentID, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );
        // board 객체에 DB에서 게시판 ID로 찾은 데이터를 저장
        UserRoleEnum userRoleEnum = user.getRole();

        Comment comment;

        if (userRoleEnum == UserRoleEnum.ADMIN) {
            comment = commentRepository.findById(commentID).orElseThrow(
                    () -> new CustomException(NOT_FOUND_COMMENT)
            );
            // 유저 권한이 ADMIN이라면 댓글 ID로 찾은 데이터를 comment 객체에 저장
        } else {
            comment = commentRepository.findByIdAndUserId(commentID, user.getId()).orElseThrow(
                    () -> new CustomException(AUTHORIZATION)
            );
            // 유저 권한이 ADMIN이 아니라면 댓글 ID와 유저의 ID가 일치하는 데이터를 DB에서 찾아서 comment 객체에 저장
        }

        commentRepository.deleteById(commentID);
        // 댓글 ID와 일치하는 데이터를 DB에서 찾아서 삭제

        return new ResponseDto(StatusEnum.OK);
    }

    @Transactional
    public ResponseDto addlike(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );
        // 댓글 ID로 DB에서 같은 데이터가 있는지 확인 -> 있으면 예외 처리 문구

        Optional<CommentLike> found = commentLikeRepository.findByCommentAndUser(comment, user);
        // comment와 user로 DB에 조회 후 데이터를 found 객체에 저장, NPE 피하기 위해서 Optional로 감싸서 선언

        if (found.isPresent()) {
            throw new CustomException(DUPLICATE_RESOURCE);
        }
        // 이미 데이터가 있을 경우 예외 처리 문구

        CommentLike commentLike = new CommentLike(comment, user);
        //comment와 user 데이터를 포함하는 commentLike 객체 생성
        comment.update_Cnt(comment.getLikeCnt() + 1);
        //comment 객채 내 메소드 활용하여 LikeCnt에 +1
        commentLikeRepository.save(commentLike);
        //DB에 commentLike 객체 데이터 저장
        return new ResponseDto("댓글 좋아요 완료", HttpStatus.OK.value());
    }

    @Transactional
    public ResponseDto deletelike(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );
        // 댓글 ID로 DB에서 데이터 저장 -> 없으면 예외 처리

        commentLikeRepository.findByCommentAndUser(comment, user).orElseThrow(
                () -> new CustomException(DUPLICATE_RESOURCE)
        );
        // comment와 user 객체 데이터로 댓글 좋아요 DB에서 일치하는 데이터 있나 확인 -> 없으면 예외 처리
        commentLikeRepository.deleteByCommentAndUser(comment, user);
        // comment와 user 데이터로 댓글 좋아요 DB에서 일치하는 데이터 삭제
        comment.update_Cnt(comment.getLikeCnt() - 1);
        // LikveCnt 1개 감소
        return new ResponseDto("댓글 좋아요 삭제", HttpStatus.OK.value());
    }
}


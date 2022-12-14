package com.sparta.spring_crud.service;

import com.sparta.spring_crud.dto.*;
import com.sparta.spring_crud.entity.*;
import com.sparta.spring_crud.repository.BoardLikeRepository;
import com.sparta.spring_crud.repository.BoardRepository;
import com.sparta.spring_crud.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sparta.spring_crud.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    // DB 접근을 위한 의존성 주입
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    public BoardDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = boardRepository.saveAndFlush(new Board(requestDto, user.getUsername(), user));
        // requestdto와 User Entity 내 정보들 DB에 저장
        return new BoardDto(board);
        // BoardDto 내 항목들 반환
    }

    @Transactional(readOnly = true)
    public ResponseDto getBoardList() {
        BoardListResponseDto boardListResponseDto = new BoardListResponseDto(StatusEnum.OK);
        // 클라이언트에 데이터 반환을 위한 객체 생성 (BoardListResponse는 ResponseDto를 상속받았기 때문에 반환값으로 설정 가능)
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        // board DB에서 생성시간 순서로 모든 데이터 취합해서 boardList 객체에 반환
        List<CommentDto> commentList = new ArrayList<>();
        // 댓글 데이터 리스트로 받기 위해서 리스트 객체 미리 생성
        for (Board board : boardList) {
            for (Comment comment : board.getComments()) {
                commentList.add(new CommentDto(comment));
            }
            boardListResponseDto.addBoard(new BoardDto(board, commentList));
            //게시글, 댓글 데이터들 for문으로 boardListResponseDto에 담기
        }
        return boardListResponseDto;
    }

    @Transactional(readOnly = true)
    public ResponseDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        // 게시글 DB에서 일치하는 id가 있는지 확인 -> 없으면 IllegalArgumentException 반환
        List<CommentDto> commentList = new ArrayList<>();
        // 댓글을 리스트형으로 받기 위해 리스트 객체 생성
        for (Comment comment : board.getComments()) {
            commentList.add(new CommentDto(comment));
        }
        // board 데이터 중에서 댓글 데이터만 for문을 이용해서 commentList 객체에 담기
        return new BoardCommentResponseDto(StatusEnum.OK, board, commentList);
        // BoardCommentResponseDto는 ResponserDto를 상속받았기 때문에 반환 가능
    }

    @Transactional
    public ResponseDto update(Long id, BoardRequestDto requestDto, User user) {
        UserRoleEnum userRoleEnum = user.getRole();
        // userRoleEnum에 현재 유저의 Role 담기

        Board board;

        if (userRoleEnum == UserRoleEnum.ADMIN) {
            board = boardRepository.findById(id).orElseThrow(
                    () -> new CustomException(NOT_FOUND_BOARD)
            );
            // 입력받은 게시글의 ID와 일치하는 데이터가 있는지 확인 -> 없으면 예외 처리

        } else {
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new CustomException(AUTHORIZATION)
                    // 입력 받은 게시글의 ID와 토큰에서 가져온 userId가 일치하는지 DB 조회 -> 없으면 예외 처리
            );
        }

        board.update(requestDto);
        // board 안에 update 메소드를 이용해서 요청받은 데이터 업데이트

        List<CommentDto> commentList = new ArrayList<>();
        // 리스트형으로 받을 객체 생성
        for (Comment comment : board.getComments()) {
            commentList.add(new CommentDto(comment));
        }
        // 리스트에 데이터 넣기

        return new BoardCommentResponseDto(StatusEnum.OK, board, commentList);
    }

    @Transactional
    public ResponseDto deleteBoard(Long id, User user) {
        UserRoleEnum userRoleEnum = user.getRole();
        // userRoleEnum에 현재 유저의 Role 담기

        Board board;

        if (userRoleEnum == UserRoleEnum.ADMIN) {
            board = boardRepository.findById(id).orElseThrow(
                    () -> new CustomException(NOT_FOUND_BOARD)
            );
            // 입력받은 게시글 ID와 동일한 데이터가 있는지 확인

        } else {
            board = boardRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new CustomException(AUTHORIZATION)
            );
        }
        // 게시글 ID와 유저의 ID가 일치하는 데이터가 있는지 확인

        boardRepository.deleteById(id);
        // 입력받은 게시글 ID와 일치하는 데이터 삭제

        return new ResponseDto(StatusEnum.OK);
    }

    public ResponseDto addlike(Long id, User user) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );
        // 게시글 DB에서 입력받은 게시글 ID와 일치하는 것이 있는지 확인

        Optional<BoardLike> found = boardLikeRepository.findByBoardAndUser(board, user);
        // NPE을 피하기 위해서 Optional로 감싸준 후 게시글 좋아요 DB에서 보드와 유저 데이터로 찾은 값을 found에 담는다.

        if (found.isPresent()) {
            throw new CustomException(DUPLICATE_RESOURCE);
        }
        // 이미 데이터가 존재한다면 예외 처리 반환 -> .isPresent는 있으면 True, 없으면 False

        BoardLike boardLike = new BoardLike(board, user);

        board.update_Cnt(board.getLikeCnt() + 1);
        // 게시판 Entity 필드 값 LikeCnt를 업데이트 해주는 메소드를 이용해서 숫자 1 증가
        boardLikeRepository.save(boardLike);
        // 게시판 좋아요 DB에 값 저장
        return new ResponseDto("게시글 좋아요 등록", HttpStatus.OK.value());
    }

    public ResponseDto deletelike(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new CustomException(NOT_FOUND_BOARD)
        );
        // 게시글 DB에서 입력받은 게시글 ID가 있나 조회 -> 없으면 예외 처리 문구
        boardLikeRepository.findByBoardAndUser(board, user).orElseThrow(
                () -> new CustomException(DUPLICATE_RESOURCE)
        );
        // 게시글 DB에서 board와 user 데이터로 조회 -> 없으면 예외 처리 문구
        board.update_Cnt(board.getLikeCnt() - 1);
        //board 내에 업데이트 메소드를 이용해서 카운트 -1
        boardLikeRepository.deleteByBoardAndUser(board, user);
        // 게시글 좋아요 DB에서 board와 user 데이터로 조회해서 일치하는 데이터 삭제
        return new ResponseDto("게시글 좋아요 삭제", HttpStatus.OK.value());
    }
}


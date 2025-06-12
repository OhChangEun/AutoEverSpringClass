package com.hd.sample_jpa_mysql_0605.service;

import com.hd.sample_jpa_mysql_0605.dto.*;
import com.hd.sample_jpa_mysql_0605.entity.Board;
import com.hd.sample_jpa_mysql_0605.entity.Member;
import com.hd.sample_jpa_mysql_0605.repository.BoardRepository;
import com.hd.sample_jpa_mysql_0605.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 게시글 등록: 반환값 Boolean
    public boolean writeBoard(BoardWriteDto boardWriteDto) {
        try {
            Board board = convertDtoToEntity(boardWriteDto);
            boardRepository.save(board);
            return true;
        } catch (Exception e) {
            log.error("게시글 등록 실패 : {}", e.getMessage());
            return false;
        }
    }
    // 게시글 전체 조회: 반환값 List<Board>
    public List<BoardResDto> loadAllBoard() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResDto> boardResDtos = new ArrayList<>(); // dto list 생성

        for (Board board : boards) {
            boardResDtos.add(convertEntityToDto(board));
        }
        return boardResDtos;
    }

    // 게시글 상세 조회: 반환값 Board
    public BoardResDto loadBoardByBoardId(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        BoardResDto boardResDto = convertEntityToDto(board);
        return boardResDto;
    }

    // 게시글 수정: 반환값 Boolean
    public boolean modifyBoard(Long boardId, BoardWriteDto boardWriteDto) {
        try {
            Board board = boardRepository.findByBoardId(boardId);
            Member member = memberRepository.findByEmail(boardWriteDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재 하지 않습니다."));
            board.setTitle(boardWriteDto.getTitle());
            board.setContent(boardWriteDto.getContent());
            board.setImage(boardWriteDto.getImg());
            board.setMember(member);
            boardRepository.save(board);
            return true;
        } catch (Exception e) {
            log.error("게시글 수정 실패: {} ", e.getMessage());
            return false;
        }
    }

    // 게시글 삭제: 반환값 Boolean
    public boolean deleteBoard(Long boardId) {
        try {
            Board board = boardRepository.findByBoardId(boardId);
            boardRepository.delete(board);
            return true;
        } catch (Exception e) {
            log.error("회원 정보 삭제 실패: {}", e.getMessage());
            return false;
        }
    }

    // 게시글 검색: 반환값 List<Board>
    public List<BoardResDto> searchBoard(String keyword) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardResDto> boardResDtos = new ArrayList<>();
        for (Board board : boards) {
            boardResDtos.add(convertEntityToDto(board));
        }
        return boardResDtos;
    }

    // 게시글 페이징 처리
    // page: 요청한 페이지 번호 (0부터 시작)
    // pageSize: 한 페이지에 표시할 게시글 개수
    public PageResDto<BoardResDto> getBoardPageList(Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        // Spring에서 제공하는 페이징 요청 객체 생성
        // 예: page = 0, pageSize = 10이면 0~9번째 게시글을 요청
        Page<Board> boardPage = boardRepository.findAll(pageRequest);
        // JPA의 PagingAndSortingRepository가 제공하는 findAll(Pageable pageable) 메서드로 게시글을 페이징 조회
        // boardPage는 Page<Board> 타입으로, 게시글 리스트 + 전체 페이지 정보 등을 포함함
        Page<BoardResDto> boardResDtoPage = boardPage.map(board -> convertEntityToDto(board));
        // Board 엔티티 객체들을 BoardResDto DTO 객체로 변환
        // Page.map()은 각 요소에 변환 로직을 적용하고 결과를 Page<T>로 반환함
        return new PageResDto<>(boardResDtoPage);
    }



    // Entity > D
    private BoardResDto convertEntityToDto(Board board) {
        BoardResDto boardResDto = new BoardResDto();
        boardResDto.setBoardId(board.getBoardId());
        boardResDto.setEmail(board.getMember().getEmail());
        boardResDto.setTitle(board.getTitle());
        boardResDto.setContent(board.getContent());
        boardResDto.setImg(board.getImage());
        boardResDto.setCreateTime(board.getCreateTime());
        return boardResDto;
    }
    // DTO > Entity
    private Board convertDtoToEntity(BoardWriteDto boardWriteDto) {
        Member member = memberRepository.findByEmail(boardWriteDto.getEmail()) // 이메일이 있는지 확인
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        Board board = new Board();
        board.setTitle(boardWriteDto.getTitle());
        board.setContent(boardWriteDto.getContent());
        board.setImage(boardWriteDto.getImg());
        board.setMember(member);
        return board;
    }
}

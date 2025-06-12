package com.hd.sample_jpa_mysql_0605.controller;

import com.hd.sample_jpa_mysql_0605.dto.BoardResDto;
import com.hd.sample_jpa_mysql_0605.dto.BoardWriteDto;
import com.hd.sample_jpa_mysql_0605.dto.PageResDto;
import com.hd.sample_jpa_mysql_0605.service.BoardService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    // 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Boolean> writeBoard(@RequestBody BoardWriteDto boardWriteDto) {
        boolean isSuccess = boardService.writeBoard(boardWriteDto);
        return ResponseEntity.ok(isSuccess);
    }

    // 게시글 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<BoardResDto>> loadAllBoard() {
        return ResponseEntity.ok(boardService.loadAllBoard());
    }

    // 특정 게시글 조회
    // 쿼리 파라미터 방식
    @GetMapping("/id")
    public ResponseEntity<BoardResDto> loadBoardById(@RequestParam Long id) {
        return ResponseEntity.ok(boardService.loadBoardByBoardId(id));
    }

    // 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<List<BoardResDto>> searchBoard(@RequestParam String keyword) {
        return ResponseEntity.ok(boardService.searchBoard(keyword));
    }

    // 게시글 수정
    @PostMapping("/modify")
    public ResponseEntity<Boolean> modifyBoard(@RequestBody BoardWriteDto boardWriteDto,
                                               @RequestParam Long id) {
        boolean isSuccess = boardService.modifyBoard(id, boardWriteDto);
        return ResponseEntity.ok(isSuccess);
    }

    // 게시글 삭제
    // 쿼리 파라미터 방식
    @GetMapping("/delete/id")
    public ResponseEntity<Boolean> deleteBoardById(@RequestParam Long id) {
        return ResponseEntity.ok(boardService.deleteBoard(id));
    }

    // 게시글 페이징
    @GetMapping("/list")
    public ResponseEntity<PageResDto> pagingBoard(@RequestParam Integer page,
                                                  @RequestParam Integer size) {
        return ResponseEntity.ok(boardService.getBoardPageList(page, size));
    }
}

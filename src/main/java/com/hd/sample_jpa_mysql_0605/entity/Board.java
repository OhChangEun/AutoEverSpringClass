package com.hd.sample_jpa_mysql_0605.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId; // board_id

    @Column(length = 256, nullable = false)
    private String title; // 게시글 제목

    @Lob // 대용량 텍스트
    private String content; // 게시글 내용
    private String image; // 게시글에 첨부될 이미지의 주소
    private LocalDateTime createTime;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now(); // db에 쓰기 직전에 시간 포함
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member; // 회원 정보 객체 가져오기

}

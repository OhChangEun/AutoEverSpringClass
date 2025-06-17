package com.hd.sample_jpa_mysql_0605.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    // 전체 조회
    private static final String SELECT_ALL = "SELECT * FROM member";


    public List<MemberResDto> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new MemberRowMapper());
    }

    private static class MemberRowMapper implements RowMapper<MemberResDto> {
        @Override
        public MemberResDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MemberResDto(
                    rs.getString("email"),
                    rs.getString("pwd"),
                    rs.getString("name"),
                    rs.getTimestamp("reg_date").toLocalDateTime()
                    );
            // RowMapper는 ResultSet에서 한 줄씩 꺼내서 객체로 바꿔주는 인터페이스입니다.
            // 이 클래스는 mini_member 테이블의 각 레코드를 MemberDto로 변환합니다.
            //rs.getString(...), rs.getTimestamp(...)를 통해 DB 컬럼의 값을 읽습니다.
        }
    }
}

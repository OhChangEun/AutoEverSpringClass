package com.hd.sample_jpa_mysql_0605.controller;

import org.springframework.web.bind.annotation.*;

@RestController // REST API 요청을 처리하는 컨트롤러다 라는 의미
// @Controller + @ResponseBody 기능을 합친 것
// 결과를 화면이 아니라 문자열, JSON 같은 "데이터"로 반환
@RequestMapping("/api") // 공통 URL 경로를 지정. GET 방식
public class RestApiTestContoroller {
    @GetMapping("/hello")
    public String getHello() {
        return "안녕하세요. 스프링부트입니다.";
    }
    @GetMapping("/board/{variable}") // URL 경로에 값을 포함하여 요청하는 방식
    public String getVariable(@PathVariable String variable) {
        return variable;
    }
    
    // http://localhost:8111/req?name=오창은&email=dwc07109@naver.com&company=OpenAI
    @GetMapping("/req") //RequestParam: 쿼리 형식으로 값을 전달하는 방식
    public String getReqParam(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String company) {
        return name  + " " + email + " " + company;
    }
}

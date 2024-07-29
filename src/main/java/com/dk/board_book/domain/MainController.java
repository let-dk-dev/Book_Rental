package com.dk.board_book.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    // 메인화면
    @GetMapping("/")
    public String openMainPage() {

        System.out.println("main");

        return "main";
    }
}

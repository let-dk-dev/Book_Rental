package com.dk.board_book.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error/show")
    public String showErrorPage() {

        return "error/errorPage"; // 에러 메시지를 보여줄 페이지의 이름
    }
}

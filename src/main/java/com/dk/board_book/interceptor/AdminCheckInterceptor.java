package com.dk.board_book.interceptor;

import com.dk.board_book.domain.member.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 세션에서 회원 정보 조회
        HttpSession session = request.getSession();
        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");

        // 2. 관리자인지 체크
        if (member.getMemberType() == '1' ) {
            return true;
        }else{
            session.setAttribute("errorMessage", "관리자만 접속 가능합니다.");
            response.sendRedirect("/error/show"); // 에러 페이지나 특정 핸들러로 리디렉션
            return false;
        }
    }
}

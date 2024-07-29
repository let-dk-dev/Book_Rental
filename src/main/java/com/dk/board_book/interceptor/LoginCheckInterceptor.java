package com.dk.board_book.interceptor;

import com.dk.board_book.domain.member.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 세션에서 회원 정보 조회
        HttpSession session = request.getSession();
        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");

        // 2. 이전 URL 저장
        //Referer은 버튼같은 이벤트를 통해서 온것만 가능, url을 직접친것은 불가능
        String previousUri = request.getRequestURI().toString();
        session.setAttribute("previousUri", previousUri);

        // 3. 회원 정보 체크
        if (member == null || member.getDeleteYn() == true) {

            System.out.println("세션에 회원정보가 없어서 로그인 페이지로 이동");

//            response.sendRedirect("/login");
            response.sendRedirect("/loginForm");

            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}


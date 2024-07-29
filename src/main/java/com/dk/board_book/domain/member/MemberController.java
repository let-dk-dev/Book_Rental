package com.dk.board_book.domain.member;

import com.dk.board_book.common.MessageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 로그인 페이지
    @GetMapping("/loginForm")
    public String openLogin(HttpServletRequest request, Model model) {

//        String previousUri = (String) request.getSession().getAttribute("previousUri");

//        model.addAttribute("previousUri", previousUri);

        return "member/login";
    }

    // 로그인
    @PostMapping("/login")
    @ResponseBody
    public MemberResponse login(HttpServletRequest request) {

        // 1. 회원 정보 조회
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");
        MemberResponse member = memberService.login(loginId, password);

        // 2. 세션에 회원 정보 저장 & 세션 유지 시간 설정
        if (member != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", member); // login 처리 순간!!
            session.setMaxInactiveInterval(60 * 30);
        }

        return member;
    }

    // 로그아웃 처리 역할
    @PostMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

//        return "redirect:/login"; // GET 방식 /login reqeust이므로,,login.html가 반환됨!!
        return "redirect:/loginForm"; // GET 방식 /login reqeust이므로,,login.html가 반환됨!!

    }


//    -----------------------------------------------------------------

    // 회원 정보 저장 (회원가입)
    @PostMapping("/members")
    @ResponseBody
    public Long saveMember(@RequestBody final MemberRequest params) {
        System.out.println("회원정보 저장 컨트롤러");

        return memberService.saveMember(params);
    }

    // 회원 상세정보 조회
    @GetMapping("/members/{loginId}")
    @ResponseBody
    public MemberResponse findMemberByLoginId(@PathVariable final String loginId) {

        return memberService.findMemberByLoginId(loginId);
    }

    // 회원 정보 수정
    @PatchMapping("/members/{mbId}")
    @ResponseBody
    public Long updateMember(HttpServletRequest request, @PathVariable final String mbId, @RequestBody final MemberRequest params) {
        Long result = memberService.updateMember(params);
        if(result > 0){
            MemberResponse updatedMember =  memberService.findMemberByMbId(mbId);
            if(mbId.equals(updatedMember.getMbId())){
                //관리자가 변화시킨게 아니라 자기가 변경했따면
                HttpSession session = request.getSession();
                session.setAttribute("loginMember", updatedMember);
            }
        }
        return result;
    }

    // 회원 정보 삭제 (회원 탈퇴)
    @DeleteMapping("/members/{mbId}")
    @ResponseBody
    public Long deleteMemberById(final Long mbId) {

        return memberService.deleteMemberById(mbId);
    }

    // 회원 수 카운팅 (ID 중복 체크)
    @GetMapping("/member-count")
    @ResponseBody
    public int countMemberByLoginId(@RequestParam final String loginId) {

        return memberService.countMemberByLoginId(loginId);
    }

    // (일반회원)이 보는 마이페이지,,admin에도 mypage가 생길 것이라 이렇게 url만듦
    @GetMapping("/member/mypage")
    public String myPage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();

        MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");

        System.out.println("로그인멤버 : " +loginMember);

        if (loginMember != null) {

            model.addAttribute("loginMember", loginMember);

            return "member/mypage";

        } else {

            // 변수 loginMember 값이 ==>> null인 경우에 대한 처리
            // ex: bean 객체 MemberResponse를 추가하거나 or 로그인 페이지로 리다이렉트 등
            return "redirect:/loginForm";
        }

    }

    // 회원탈퇴 기능
    @PostMapping("/member/withdraw")
    @ResponseBody
    public String withdraw(HttpServletRequest request, Model model){

        HttpSession session = request.getSession();

        MemberResponse loginMember = (MemberResponse) session.getAttribute("loginMember");

        memberService.deleteMemberById(loginMember.getMbId());

        return "회원탈퇴성공";
    }

    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {

        model.addAttribute("params", params);

        return "common/messageRedirect";
    }

}

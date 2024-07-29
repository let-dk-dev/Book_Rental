package com.dk.board_book.domain.admin;

import com.dk.board_book.common.dto.SearchDto;
import com.dk.board_book.common.paging.PagingResponse;
import com.dk.board_book.domain.book.BookService;
import com.dk.board_book.domain.member.MemberRequest;
import com.dk.board_book.domain.member.MemberResponse;
import com.dk.board_book.domain.member.MemberService;
import com.dk.board_book.domain.rent.RentResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    private final BookService bookService;

    private final AdminService adminService;

    @GetMapping("/admin/login")
    public String openLogin() {

        return "admin/login";
    }

    // 회원 정보 리스트
    @GetMapping("/admin/member/list")
    public String getMemberList(@ModelAttribute("params") final SearchDto params, Model model) {

        PagingResponse<MemberResponse> response = memberService.findAllMembers(params);

        model.addAttribute("response", response);

        return "member/list";
    }


    // 관리자의,,회원정보 상세보기
    @GetMapping("/admin/member/view")
    public String findMemberByLoginId(HttpServletRequest request, @RequestParam(value = "loginId", required = false) final String loginId, Model model) {

        HttpSession session = request.getSession();

        MemberResponse loginAdmin = (MemberResponse) session.getAttribute("loginMember");

        if (loginId != null) {

            MemberResponse targetMember = memberService.findMemberByLoginId(loginId);
            model.addAttribute("targetMember", targetMember);
            model.addAttribute("loginAdmin", loginAdmin);
        }

        return "admin/memberView";
    }

     // 대여중인 도서 리스트 페이지
    @GetMapping("/admin/book/list")
    public String openBookList(HttpServletRequest request, @ModelAttribute("params") final SearchDto params, Model model) {

        PagingResponse<RentResponse> response = adminService.findAllRentedBooks(params);

        model.addAttribute("response", response);

        return "admin/rentedBookList";
    }

    // 관리자의 유저 회원탈퇴 기능
    @PatchMapping("/admin/memberWithdraw/{targetMemberId}")
    @ResponseBody
    public String withdraw(HttpServletRequest request, Model model, @PathVariable final Long targetMemberId){
        System.out.println("관리자의 유저 회원탈퇴 기능");
        System.out.println(targetMemberId);
        memberService.deleteMemberById(targetMemberId);
        return "회원탈퇴성공";
    }



}

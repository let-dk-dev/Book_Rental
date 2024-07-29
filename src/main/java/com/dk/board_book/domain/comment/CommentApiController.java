package com.dk.board_book.domain.comment;

import com.dk.board_book.common.paging.PagingResponse;
import com.dk.board_book.domain.member.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    // 신규 댓글 생성
    @PostMapping("/posts/{bdId}/comments")
    public CommentResponse saveComment(HttpServletRequest request,  @PathVariable final Long bdId, @RequestBody final CommentRequest params) {

        HttpSession session = request.getSession();

        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");

        params.setWriter(member.getMbId());

        Long reId = commentService.saveComment(params);

        return commentService.findCommentById(reId);
    }

    // 전체 댓글 list 조회
   @GetMapping("/posts/{bdId}/comments")
    public PagingResponse<CommentResponse> findAllComment(@PathVariable final Long bdId, final CommentSearchDto params) {

        return commentService.findAllComment(params);
    }

    // 댓글 상세정보 조회
    @GetMapping("/posts/{bdId}/comments/{reId}")
    public CommentResponse findCommentById(@PathVariable final Long bdId, @PathVariable final Long reId) {

          return commentService.findCommentById(reId);
}


    // 기존 댓글 수정
    @PatchMapping("/posts/{bdId}/comments/{reId}")
    public CommentResponse updateComment(@PathVariable final Long bdId, @PathVariable final Long reId, @RequestBody final CommentRequest params) {

            commentService.updateComment(params);

            return commentService.findCommentById(reId);
    }

    // 댓글 삭제
    @DeleteMapping("/posts/{bdId}/comments/{reId}")
    public Long deleteComment(@PathVariable final Long bdId, @PathVariable final Long reId) {

        return commentService.deleteComment(reId);
    }
}

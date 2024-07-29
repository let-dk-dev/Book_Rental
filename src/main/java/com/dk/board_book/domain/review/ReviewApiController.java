package com.dk.board_book.domain.review;

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

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    // 신규 리뷰 생성
    @PostMapping("/books/{bkId}/reviews")
    public ReviewResponse saveReview(HttpServletRequest request,  @PathVariable final Long bkId, @RequestBody final ReviewRequest params) {

        HttpSession session = request.getSession();

        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");

        params.setWriter(member.getMbId());

        Long reviewId = reviewService.saveReview(params);

        return reviewService.findReviewById(reviewId);
    }

    // 전체 리뷰 리스트 조회
    @GetMapping("/books/{bkId}/reviews")
    public PagingResponse<ReviewResponse> findAllReview(@PathVariable final Long bkId, final ReviewSearchDto params) {
        return reviewService.findAllReview(params);
    }

    // 리뷰 상세정보 조회
    @GetMapping("/books/{bkId}/reviews/{reviewId}")
    public ReviewResponse findReviewById(

        @PathVariable final Long bkId, @PathVariable final Long reviewId) {

        return reviewService.findReviewById(reviewId);
    }

    // 기존 리뷰 수정
    @PatchMapping("/books/{bkId}/reviews/{reviewId}")
    public ReviewResponse updateReview(

        @PathVariable final Long bkId,
        @PathVariable final Long reviewId,
        @RequestBody final ReviewRequest params
    ) {

        reviewService.updateReview(params);

        return reviewService.findReviewById(reviewId);
    }

    // 리뷰 삭제
    @DeleteMapping("/books/{bkId}/reviews/{reviewId}")
    public Long deleteReview(@PathVariable final Long bkId, @PathVariable final Long reviewId) {

        return reviewService.deleteReview(reviewId);
    }

}

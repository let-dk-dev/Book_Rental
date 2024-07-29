package com.dk.board_book.domain.review;

import com.dk.board_book.common.paging.Pagination;
import com.dk.board_book.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    /**
     * 리뷰 저장
     * @param params - 리뷰 정보
     * @return Generated PK
     */
    @Transactional
    public Long saveReview(final ReviewRequest params) {

        reviewMapper.save(params);

        return params.getReviewId();
         /*
           반환값은 ==>> 필드 reviewId 값,,
           -----------------------------------------
           따라서,,
           반환 type은 ==>> Long
         */
    }

   /**
     * 리뷰 상세정보 조회
     * @param reviewId - PK
     * @return 리뷰 상세정보
     */
     public ReviewResponse findReviewById(final Long reviewId) {

        return reviewMapper.findById(reviewId);
    }

    /**
     * 리뷰 수정
     * @param params - 리뷰 정보
     * @return PK
     */
     @Transactional
    public Long updateReview(final ReviewRequest params) {

        reviewMapper.update(params);

        return params.getReviewId();
    }

    /**
     * 리뷰 삭제
     * @param reviewId - PK
     * @return PK
     */
    @Transactional
    public Long deleteReview(final Long reviewId) {

        reviewMapper.deleteById(reviewId);

        return reviewId;
    }

    /**
     * 리뷰 리스트 조회
     * @param params - search conditions
     * @return list & pagination information
     */
    public PagingResponse<ReviewResponse> findAllReview(final ReviewSearchDto params) {

        int count = reviewMapper.count(params);

        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, params);

        List<ReviewResponse> list = reviewMapper.findAll(params);

        return new PagingResponse<>(list, pagination);
    }
}

package com.dk.board_book.domain.review;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    /**
     * 리뷰 저장
     * @param params - 리뷰 정보
     */
    void save(ReviewRequest params);

    /**
     * 리뷰 상세정보 조회
     * @param reviewId - PK
     * @return 리뷰 상세정보
     */
    ReviewResponse findById(Long reviewId);

    /**
     * 리뷰 수정
     * @param params - 리뷰 정보
     */
    void update(ReviewRequest params);

    /**
     * 리뷰 삭제
     * @param reviewId - PK
     */
    void deleteById(Long reviewId);

    /**
     * 리뷰 리스트 조회
     * @param params - search conditions
     * @return 리뷰 리스트
     */
    List<ReviewResponse> findAll(ReviewSearchDto params);

    /**
     * 리뷰 수 카운팅
     * @param params - search conditions
     * @return 리뷰 수
     */
    int count(ReviewSearchDto params);
}

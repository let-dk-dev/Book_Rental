package com.dk.board_book.domain.review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRequest {

    private Long reviewId;     // 리뷰 번호 (PK)

    private Long bkId;       // 도서 번호 (FK)

    private String content;    // 내용

    private Long writer;     // 작성자(리뷰어 id)

}

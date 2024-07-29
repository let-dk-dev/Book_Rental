package com.dk.board_book.domain.review;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse {

    private Long reviewId;                 // 리뷰 번호 (PK)

    private Long bkId;                   // 도서 번호 (FK)

    private String content;                // 내용

    private String writer;                 // 작성자

    private String name;                   // 작성자이름

    private Boolean deleteYn;              // 삭제 여부
    private LocalDateTime crDate;          // 생성일시
    private LocalDateTime mdDate;          // 최종 수정일시
}

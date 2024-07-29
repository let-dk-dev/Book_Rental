package com.dk.board_book.domain.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private Long reId;                       // 댓글 번호 (PK)
    private Long bdId;                   // 게시글 번호 (FK)
    private String content;                // 내용
    private String writer;                 // 작성자

    private String name;                   // 작성자이름

    private Boolean deleteYn;              // 삭제 여부
    private LocalDateTime createdDate;     // 생성일시
    private LocalDateTime modifiedDate;    // 최종 수정일시
}

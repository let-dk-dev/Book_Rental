package com.dk.board_book.domain.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private Long boardId;
    private String title;
    private String content;

    private Long writerId;                     // 작성자id
    private String writerName;                 // 작성자이름

    private int viewCnt;

    private Boolean noticeYn;
    private Boolean deleteYn;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}

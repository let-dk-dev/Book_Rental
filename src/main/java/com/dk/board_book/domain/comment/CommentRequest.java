package com.dk.board_book.domain.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequest {

    private Long reId;
    private Long bdId;
    private String content;

    private Long writer;
}

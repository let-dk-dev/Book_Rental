package com.dk.board_book.domain.comment;

import com.dk.board_book.common.dto.SearchDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSearchDto extends SearchDto {

    private Long bdId;
}

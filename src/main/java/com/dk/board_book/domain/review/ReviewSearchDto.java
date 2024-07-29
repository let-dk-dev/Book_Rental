package com.dk.board_book.domain.review;

import com.dk.board_book.common.dto.SearchDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSearchDto extends SearchDto {

    private Long bkId;    // 도서 번호 (FK)
}

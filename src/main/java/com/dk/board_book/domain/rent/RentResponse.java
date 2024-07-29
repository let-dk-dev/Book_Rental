package com.dk.board_book.domain.rent;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RentResponse {
    private Long rentalNo;              //대여id

    private Long bkId;                  //북id
//    private Long bookId;              // (X),,잘못된 수정인 것으로 결론!!

    private String bookName;            // 책이름

    private String isbn;                //책 isbn

    private Long mbId;                  //대여자

    private String coverFile;           //커버이미지

    private String memberName;          //대여자이름

    private LocalDateTime rentalDate;   //대여일
    private LocalDateTime withdrawDate; //반납예정일
    private LocalDateTime returnDate;   //실제반납일

    private LocalDateTime crDate;       // 생성일시
    private Long crMemberId;
    private LocalDateTime mdDate;       // 최종 수정일시
    private Long mdMemberId;

}

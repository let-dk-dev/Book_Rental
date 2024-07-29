package com.dk.board_book.domain.book;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class BookResponse {

    private Long bookId;
    private String isbn;
    private String bookName;
    private String coverFile;
    private String writer;
    private String pbComp;

//    private LocalDateTime pbDate;
    private LocalDate pbDate;  // 도서 수정 시,,출판일 data가,,2020-01-10T00:00:00 형태로 불러와져서,,error가 발생하므로,,수정!!
    private Integer price;

    private String rentalAvailableYn;  //대여가능여부 "Y","N"

    private LocalDateTime crDate;// 생성일시
    private Long crMemberId;

    private LocalDateTime mdDate; // 최종 수정일시
    private Long mdMemberId;

    private String crMemberName;    //생성자 이름
    private String mdMemberName;    //수정자 이름

    @Override
    public String toString() {
        return "BookResponse{" +
                "bookId=" + bookId +
                ", isbn='" + isbn + '\'' +
                ", bookName='" + bookName + '\'' +
                ", coverFile='" + coverFile + '\'' +
                ", writer='" + writer + '\'' +
                ", pbComp='" + pbComp + '\'' +
                ", pbDate=" + pbDate +
                ", price=" + price +
                ", rentalAvailableYn='" + rentalAvailableYn + '\'' +
                ", crDate=" + crDate +
                ", crMemberId=" + crMemberId +
                ", mdDate=" + mdDate +
                ", mdMemberId=" + mdMemberId +
                ", crMemberName='" + crMemberName + '\'' +
                ", mdMemberName='" + mdMemberName + '\'' +
                '}';
    }
}


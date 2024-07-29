package com.dk.board_book.domain.book;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookRequest {

   private Long bookId;
    private String isbn;
    private String bookName;
    private String coverFile;
    private String writer;
    private String pbComp;
    private LocalDate pbDate;
    private Integer price;

    private String rentalAvailableYn;  //대여가능여부 "Y","N"

    private List<MultipartFile> files = new ArrayList<>();    // 첨부파일 List
    private List<Long> removeFileIds = new ArrayList<>();     // 삭제할 첨부파일 id List
    private Long crMemberId;    //생성자
    private Long mdMemberId;    //수정자

 @Override
 public String toString() {
  return "BookRequest{" +
          "bookId=" + bookId +
          ", isbn='" + isbn + '\'' +
          ", bookName='" + bookName + '\'' +
          ", coverFile='" + coverFile + '\'' +
          ", writer='" + writer + '\'' +
          ", pbComp='" + pbComp + '\'' +
          ", pbDate=" + pbDate +
          ", price=" + price +
          ", rentalAvailableYn='" + rentalAvailableYn + '\'' +
          ", files=" + files +
          ", removeFileIds=" + removeFileIds +
          ", crMemberId=" + crMemberId +
          ", mdMemberId=" + mdMemberId +
          '}';
 }
}

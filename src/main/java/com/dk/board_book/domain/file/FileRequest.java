package com.dk.board_book.domain.file;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class FileRequest {

    private Long fileId;            // 파일 번호 (PK)
    
    private Long bdId;              // 게시글 번호 (FK)

    private Long bkId;              // 도서 번호 (FK)
    private String originalName;    // 원본 파일명
    private String saveName;        // 저장 파일명
    private long size;              // 파일 크기

    private String uploadDateFolder;      // 파일등록일을 기반으로 한 폴더

    @Builder
    public FileRequest(String originalName, String saveName, long size) {

        this.originalName = originalName;
        this.saveName = saveName;
        this.size = size;

        this.uploadDateFolder = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    }

    public void setBdId(Long bdId) {
        this.bdId = bdId;
    }

    public void setBkId(Long bkId) {
        this.bkId = bkId;
    }
}

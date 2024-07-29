package com.dk.board_book.domain.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostRequest {

    private Long boardId;
    private String title;
    private String content;

    private Long writerId;  // 작성자id

    private Boolean noticeYn;

    private List<MultipartFile> files = new ArrayList<>();    // 첨부파일 List

    private List<Long> removeFileIds = new ArrayList<>(); // 삭제할 첨부파일 id List
}


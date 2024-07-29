package com.dk.board_book.domain.file;

import com.dk.board_book.common.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileApiController {

    private final FileService fileService;

    private final FileUtils fileUtils;

    // (게시글 관련 file) 리스트 조회
    @GetMapping(value = {"/posts/{bdId}/files"})
    public List<FileResponse> findAllFileByBdId(@PathVariable final Long bdId) {
        return fileService.findAllFileByBdId(bdId);
    }

    // 첨부파일 다운로드 (url에 bkId가 있어도,,매개변수 자리에,,Long bkId는 없어야,,download 작동함)
    @GetMapping(value = {"/posts/{bdId}/files/{fileId}/download", "/books/{bkId}/files/{fileId}/download"})
    public ResponseEntity<Resource> downloadFile(@PathVariable final Long bdId, @PathVariable final Long fileId) {

        FileResponse file = fileService.findFileById(fileId);  // db 상
        Resource resource = fileUtils.readFileAsResource(file);  // 물리적 disk 상

        try {
            String filename = URLEncoder.encode(file.getOriginalName(), "UTF-8");

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + filename + "\";")
                    .header(HttpHeaders.CONTENT_LENGTH, file.getSize() + "")
                    .body(resource);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("filename encoding failed : " + file.getOriginalName());
        }
    }




}


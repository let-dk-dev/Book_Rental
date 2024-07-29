package com.dk.board_book.domain.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
                 /*
                   FileService는 ==>> db 상의 data 처리
                   ------------------------------------
                   FileUtils는 ==>> 물리적인 disk 상의 처리
                   --------------------------------------
                   그런데,,
                   logic 재료로 사용되는,,
                   data 보따리 객체 FileRequest 등의,,
                   data는,,
                     V V
                   객체 FileUtils로부터,,얻음!!
                 */
    private final FileMapper fileMapper;

    /**
     * 파일 정보 저장 (to Database)
     * @param postId - 게시글 번호 (FK)
     * @param files - 파일 정보 리스트
     */
    @Transactional
    public void saveFiles(final Long bdId, final List<FileRequest> files) {

        if (CollectionUtils.isEmpty(files)) {
            return;
        }

        for (FileRequest file : files) {
            file.setBdId(bdId);
        }

        fileMapper.saveAll(files);
    }

    /**
      * 책 썸네일 파일 정보 저장 (to Database)
      * @param bkId - 도서 번호 (FK)
      * @param files - 파일 정보 리스트
     */
     @Transactional
     public void saveThumbnailFiles(final Long bkId, final List<FileRequest> files) {

         if (CollectionUtils.isEmpty(files)) {

             return;
         }

         for (FileRequest file : files) {

             file.setBkId(bkId);
            }

            fileMapper.saveAll(files);
     }

    /**
     * 파일 리스트 조회
     * @param postId - 게시글 번호 (FK)
     * @return 파일 리스트
     */
    public List<FileResponse> findAllFileByBdId(final Long bdId) {

        return fileMapper.findAllByBdId(bdId);
    }

    /**
     * 파일 리스트 조회
     * @param ids - PK 리스트
     * @return 파일 리스트
     */
    public List<FileResponse> findAllFileByIds(final List<Long> fileIds) {

        if (CollectionUtils.isEmpty(fileIds)) {

            return Collections.emptyList();
        }

        return fileMapper.findAllByIds(fileIds);
    }

    /**
     * 썸네일 파일 리스트 조회
     * @param bookId - 책 번호 (FK)
     * @return 파일 리스트
     */
    public List<FileResponse> findAllFileByBookId(final Long bkId) {

        return fileMapper.findAllByBookId(bkId);
    }

    /**
     * 파일 삭제 (from Database)
     * @param ids - PK 리스트
     */
    @Transactional
    public void deleteAllFileByIds(final List<Long> fileIds) {

        if (CollectionUtils.isEmpty(fileIds)) {

            return;
        }

        fileMapper.deleteAllByIds(fileIds);
    }

      /**
     * 파일 상세정보 조회
     * @param fileId - PK
     * @return 파일 상세정보
     */
    public FileResponse findFileById(final Long fileId) {
        return fileMapper.findById(fileId);
    }


}


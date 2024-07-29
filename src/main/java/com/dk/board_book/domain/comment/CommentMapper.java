package com.dk.board_book.domain.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 댓글 저장
     * @param params - 댓글 정보
     */
    void save(CommentRequest params); // C

    /**
     * 댓글 상세정보 조회
     * @param id - PK
     * @return 댓글 상세정보
     */
    CommentResponse findById(Long reId); // R

    /**
     * 댓글 수정
     * @param params - 댓글 정보
     */
    void update(CommentRequest params); // U

   /**
    * 댓글 삭제
    * @param id - PK
    */
    void deleteById(Long reId); // D

   /**
    * 댓글 리스트 조회
    * @param params - search conditions
    * @return 댓글 리스트
    */
    List<CommentResponse> findAll(CommentSearchDto params); // all_R

   /**
    * 댓글 수 카운팅
    * @param params - search conditions
    * @return 댓글 수
    */
    int count(CommentSearchDto params);
}

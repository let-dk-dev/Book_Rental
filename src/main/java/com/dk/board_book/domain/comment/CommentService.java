package com.dk.board_book.domain.comment;

import com.dk.board_book.common.paging.Pagination;
import com.dk.board_book.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

   /**
    * 댓글 저장
    * @param params - 댓글 정보
    * @return Generated PK
    */
    @Transactional
    public Long saveComment(final CommentRequest params) {

        commentMapper.save(params);

        return params.getReId();
//        return params.getBdId();
    }

   /**
    * 댓글 상세정보 조회
    * @param id - PK
    * @return 댓글 상세정보
    */
    public CommentResponse findCommentById(final Long reId) {

        return commentMapper.findById(reId);
    }

   /**
    * 댓글 수정
    * @param params - 댓글 정보
    * @return PK
    */
    @Transactional
    public Long updateComment(final CommentRequest params) {

        commentMapper.update(params);
        return params.getReId();
    }

   /**
    * 댓글 삭제
    * @param id - PK
    * @return PK
    */
    @Transactional
    public Long deleteComment(final Long reId) {

        commentMapper.deleteById(reId);

        return reId;
    }

   /**
    * 댓글 리스트 조회
    * @param params - search conditions
    * @return list & pagination information
    */
    public PagingResponse<CommentResponse> findAllComment(final CommentSearchDto params) {

        int count = commentMapper.count(params);

        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, params);
        List<CommentResponse> list = commentMapper.findAll(params);
        return new PagingResponse<>(list, pagination);
    }

}

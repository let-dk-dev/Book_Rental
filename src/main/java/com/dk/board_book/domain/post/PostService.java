package com.dk.board_book.domain.post;

import com.dk.board_book.common.dto.SearchDto;
import com.dk.board_book.common.paging.Pagination;
import com.dk.board_book.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    /**
     * 게시글 저장
     * @param params - 게시글 정보
     * @return Generated PK
     */
    @Transactional
    public Long savePost(final PostRequest params) {

        postMapper.save(params);

        return params.getBoardId();
    }

    /**
     * 게시글 상세정보 조회
     * @param boardId - PK
     * @return 게시글 상세정보
     */
    public PostResponse findPostById(final Long boardId) {

        return postMapper.findById(boardId);
    }

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     * @return PK
     */
    @Transactional
    public Long updatePost(final PostRequest params) {

        postMapper.update(params);

        return params.getBoardId();
    }

    /**
     * 게시글 삭제
     * @param boardId - PK
     * @return PK
     */
    public Long deletePost(final Long boardId) {

        postMapper.deleteById(boardId);

        return boardId;
    }

    /**
     * 전체 게시글 list 조회
     * @param params - search conditions
     * @return list & pagination information
     */
    public PagingResponse<PostResponse> findAllPost(final SearchDto params) {

        int count = postMapper.count(params);

        if (count < 1) {

            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, params);

        params.setPagination(pagination);

        List<PostResponse> list = postMapper.findAll(params);

        return new  PagingResponse<>(list, pagination);
    }

    /**
     * 게시글 조회 수 증가
     * @param params - 게시글 정보
     * @return PK
     */
    @Transactional
    public Long addViewCount(final Long boardId) {

        postMapper.addViewCount(boardId);

        return boardId;
    }


}

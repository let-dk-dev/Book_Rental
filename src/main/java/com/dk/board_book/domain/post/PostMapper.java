package com.dk.board_book.domain.post;

import com.dk.board_book.common.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    void save(PostRequest params);

    PostResponse findById(Long boardId);

    void update(PostRequest params);

    void deleteById(Long boardId);

    List<PostResponse> findAll(SearchDto params);

    int count(SearchDto params);

    /**
     * 게시글 뷰수 1회 증가
     *
     * @param id - pk
     */
     void addViewCount(Long boardId);
}

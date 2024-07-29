package com.dk.board_book;

import com.dk.board_book.domain.post.PostRequest;
import com.dk.board_book.domain.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    void save() {

        PostRequest params = new PostRequest();

        params.setTitle("1번 게시글 제목");
        params.setContent("1번 게시글 내용");
        params.setWriterId(2l);
        params.setNoticeYn(false);

        Long boardId = postService.savePost(params);

        System.out.println("insert된 게시글 id ==>> " + boardId);
    }

    @Test
    void saveByForeach() {
        for (int i = 1; i <= 1000; i++) {

            PostRequest params = new PostRequest();

            params.setTitle(i + "번 게시글 제목");
            params.setContent(i + "번 게시글 내용");
            params.setWriterId(2l);
            params.setNoticeYn(false);

            postService.savePost(params);
        }
    }
}

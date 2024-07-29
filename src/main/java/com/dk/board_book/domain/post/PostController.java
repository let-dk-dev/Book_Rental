package com.dk.board_book.domain.post;

import com.dk.board_book.common.FileUtils;
import com.dk.board_book.common.MessageDto;
import com.dk.board_book.common.dto.SearchDto;
import com.dk.board_book.common.paging.PagingResponse;
import com.dk.board_book.domain.file.FileRequest;
import com.dk.board_book.domain.file.FileResponse;
import com.dk.board_book.domain.file.FileService;
import com.dk.board_book.domain.member.MemberResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileService fileService;
    private final FileUtils fileUtils;

    // 게시글 작성 페이지
    @GetMapping("/post/write")
    public String openPostWrite(
            @RequestParam(value = "boardId", required = false) final Long boardId,
            Model model) {

//        String title = "제목",
//               content = "내용",
//               writer = "홍길동";
//
//        model.addAttribute("t", title);
//        model.addAttribute("c", content);
//        model.addAttribute("w", writer);

        if (boardId != null) {

            PostResponse post = postService.findPostById(boardId);

            model.addAttribute("post", post);
        }
        return "post/write";
    }

    // 신규 게시글 생성
    @PostMapping("/post/save")
    public String savePost(HttpServletRequest request, final PostRequest params, Model model) {

        HttpSession session = request.getSession();

        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");

        params.setWriterId(member.getMbId());

        Long bdId = postService.savePost(params);

        List<FileRequest> files = fileUtils.uploadFiles(params.getFiles());

        fileService.saveFiles(bdId, files);

        MessageDto message = new MessageDto("게시글 생성 완료!!", "/post/list", RequestMethod.GET, null);

//        return "redirect:/post/list";
        return showMessageAndRedirect(message, model);
    }

    // 게시글 리스트 페이지
    @GetMapping("/post/list")
    public String openPostList(@ModelAttribute("params") final SearchDto params, Model model) {

        PagingResponse<PostResponse> response = postService.findAllPost(params);

        model.addAttribute("response", response);

        return "post/list";
    }

    // 게시글 상세 페이지
    @GetMapping("/post/view")
    public String openPostView(@RequestParam final Long boardId, Model model) {

        System.out.println("boardId ==>> " + boardId);

        System.out.println("상세보기 controller class /post/view");

        PostResponse post = postService.findPostById(boardId);
        System.out.println("상세보기 service class 실행 후");

        postService.addViewCount(boardId);
        System.out.println("상세보기에 의한,,조회수 1 증가");

        model.addAttribute("post", post);
        System.out.println("객체 Model에,,객체 PostResponse 담아 ==>> view 단으로 전달");

        return "post/view";
    }

    // 기존 게시글 수정
    @PostMapping("/post/update")
    public String updatePost(final PostRequest params, final SearchDto queryParams, Model model) {
        /*
          게시글 수정 클릭 시,,
            V V
          form 요소 하위의,,
          <input type="hidden" id="writerId" name="writerId" th:if="${post != null}" th:value="${post.writerId}" />에 의해,,
            V V
           writerId data가,,
           form-data에 포함되면서,,
           handler 메서드()로 넘어가,,
             V V
           객체 PostRequest의,,
           필드 writerId에 담기게 됨!!
        */

        // 1. 게시글 수정 역할(반환값은 ==>> boardId)
        postService.updatePost(params);

        // 2. 파일 업로드 (to disk)
        List<FileRequest> uploadFiles = fileUtils.uploadFiles(params.getFiles());

        // 3. 파일 정보 저장 (to database)
        fileService.saveFiles(params.getBoardId(), uploadFiles);

        // 4. 삭제할 파일 정보 조회 (from database)
        List<FileResponse> deleteFiles = fileService.findAllFileByIds(params.getRemoveFileIds());

        // 5. 파일 삭제 (from disk)
        fileUtils.deleteFiles(deleteFiles);

        // 6. 파일 삭제 (from database)
        fileService.deleteAllFileByIds(params.getRemoveFileIds());

        MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/list", RequestMethod.GET, queryParamsToMap(queryParams));

//        return "redirect:/post/list";
        return showMessageAndRedirect(message, model);
    }

    // 게시글 삭제
    @PostMapping("/post/delete")
    public String deletePost(@RequestParam final Long boardId, final SearchDto queryParams, Model model) {

        postService.deletePost(boardId);

        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list", RequestMethod.GET, queryParamsToMap(queryParams));

//        return "redirect:/post/list";
        return showMessageAndRedirect(message, model);
    }

    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    public String showMessageAndRedirect(final MessageDto params, Model model) { // handler 메서드()의,,보조(파생된) 메서드()

        model.addAttribute("params", params);

        return "common/messageRedirect";
    }

    // 쿼리 스트링 파라미터를 Map에 담아 반환
    private Map<String, Object> queryParamsToMap(final SearchDto queryParams) { // handler 메서드()의,,보조(파생된) 메서드()

        Map<String, Object> data = new HashMap<>();

        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());

        return data;
    }
}

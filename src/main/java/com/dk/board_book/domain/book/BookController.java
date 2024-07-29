package com.dk.board_book.domain.book;

import com.dk.board_book.common.FileUtils;
import com.dk.board_book.common.MessageDto;
import com.dk.board_book.common.dto.SearchDto;
import com.dk.board_book.common.paging.PagingResponse;
import com.dk.board_book.domain.file.FileRequest;
import com.dk.board_book.domain.file.FileResponse;
import com.dk.board_book.domain.file.FileService;
import com.dk.board_book.domain.member.MemberResponse;
import com.dk.board_book.domain.rent.RentRequest;
import com.dk.board_book.domain.rent.RentResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final FileService fileService;
    private final FileUtils fileUtils;

    // 도서정보 입력 form 페이지를 반환하는 역할
    @GetMapping("/book/write")
    public String openBookWrite(@RequestParam(value = "bookId", required = false) final Long bookId, Model model) {

        System.out.println("bookId ==>>>>> " + bookId);

        if (bookId != null) {

            BookResponse book = bookService.findBookById(bookId);

            model.addAttribute("book", book);
        }

        return "book/write";
    }

    // 신규 도서 생성(저장)하는 역할
    @PostMapping("/book/save")
    public String saveBook(HttpServletRequest request, final BookRequest params, Model model) {
        try{
            List<FileRequest> files = fileUtils.uploadFiles(params.getFiles());
            System.out.println(params);
            System.out.println("책저장시 받은 파일 : "+files);
            System.out.println(files.size());

            if(files.size() != 0){
                params.setCoverFile(files.get(0).getUploadDateFolder() +"/"+ files.get(0).getSaveName());
            }

            // ISBN 길이 검증
            if (params.getIsbn() != null && params.getIsbn().replaceAll("[-\\s]", "").length() > 13) { // 공백과 -를 뺀 ISBN 길이가 13자를 초과하면 안 됨
                model.addAttribute("error", "ISBN 번호는 13자리를 초과할 수 없습니다.");
                MessageDto message = new MessageDto("ISBN 길이가 13자를 초과하면 안 됩니다.", "/book/list", RequestMethod.GET, null);
                return showMessageAndRedirect(message, model);
            }


            //생성자, 수정자로 로그인유저
            HttpSession session = request.getSession();
            MemberResponse member = (MemberResponse) session.getAttribute("loginMember");
            params.setCrMemberId(member.getMbId());
            params.setMdMemberId(member.getMbId());


            Long bkId = bookService.saveBook(params); // point code 위아래 순서 변경

            fileService.saveThumbnailFiles(bkId, files);  //파일첨부와 거의 동일하게 복제

            MessageDto message = new MessageDto("도서등록이 완료되었습니다.", "/book/list", RequestMethod.GET, null);

            return showMessageAndRedirect(message, model);

        } catch(IndexOutOfBoundsException e){
            model.addAttribute("error", "ISBN 번호 처리 중 오류가 발생했습니다. 정확한 ISBN 번호를 입력해주세요.");
            return "errorPage"; // 에러 페이지로 리디렉트
        } catch (Exception e) {
            // 다른 예외들 처리
            model.addAttribute("error", "책 저장 중 예기치 않은 오류가 발생했습니다.");
            return "errorPage"; // 에러 페이지로 리디렉트
        }

    }

    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) { // handler 메서드()의,,보조(파생된) 메서드()

            model.addAttribute("params", params);

            return "common/messageRedirect";
    }

    // 전체 도서 list 페이지를 반환하는 역할
    @GetMapping("/book/list")
    public String openBookList(HttpServletRequest request, @ModelAttribute("params") final SearchDto params, Model model) {
        PagingResponse<BookResponse> response = bookService.findAllBooks(params);

        model.addAttribute("response", response);

        HttpSession session = request.getSession();

        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");

        List<RentResponse> rentList = bookService.findRentedBooksByUserId(member.getMbId());

        model.addAttribute("rentList", rentList);

        return "book/list";
    }

    // 도서 상세 페이지를 반환하는 역할
    @GetMapping("/book/view")
    public String openBookView(HttpServletRequest request, @RequestParam final Long bookId, Model model) {

        BookResponse book = bookService.findBookById(bookId);

        model.addAttribute("book", book);

        HttpSession session = request.getSession();

        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");

        List<RentResponse> rentList = bookService.findRentedBooksByUserId(member.getMbId());

        boolean isRented = rentList.stream().anyMatch(bookRentResponse -> bookRentResponse.getBkId().equals(bookId));
//        boolean isRented = rentList.stream().anyMatch(bookRentResponse -> bookRentResponse.getBookId().equals(bookId));

        model.addAttribute("isRented", isRented);

        return "book/view";
    }

    // 기존 도서정보 수정하는 역할
    @PostMapping("/book/update")
    public String updateBook(final BookRequest params, final SearchDto queryParams, Model model) {
        System.out.println("도서정보 수정");
        List<FileRequest> files = fileUtils.uploadFiles(params.getFiles());
        if(files.size() != 0){
            params.setCoverFile(files.get(0).getUploadDateFolder() +"/"+ files.get(0).getSaveName());
        }
        bookService.updateBook(params);
        MessageDto message = new MessageDto("도서 수정이 완료되었습니다.", "/book/list", RequestMethod.GET, queryParamsToMap(queryParams));
        return showMessageAndRedirect(message, model);
    }

    // 쿼리 스트링 파라미터를 Map에 담아 반환하는 역할
    private Map<String, Object> queryParamsToMap(final SearchDto queryParams) {

            Map<String, Object> data = new HashMap<>();

            data.put("page", queryParams.getPage());

            data.put("recordSize", queryParams.getRecordSize());

            data.put("pageSize", queryParams.getPageSize());

            data.put("keyword", queryParams.getKeyword());

            data.put("searchType", queryParams.getSearchType());

            return data;
    }

    // 도서 삭제하는 역할
    @PostMapping("/book/delete")
    public String deleteBook(@RequestParam final Long bookId, final SearchDto queryParams, Model model) {

        bookService.deleteBookById(bookId);

        MessageDto message = new MessageDto("도서 삭제가 완료되었습니다.", "/book/list", RequestMethod.GET, queryParamsToMap(queryParams));

        return showMessageAndRedirect(message, model);
    }

    //대여정보--(대여 건) 저장하는 역할
    @PostMapping("/book/rent")
    @ResponseBody
    public MessageDto rentBook(HttpServletRequest request, @RequestBody RentRequest params, Model model) {

        HttpSession session = request.getSession();
        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");
        MessageDto message = new MessageDto("초기화", "/book/list", RequestMethod.GET, null);

        try {
            // 대여 서비스 호출 (대여 로직 구현 필요)
            params.setMbId(member.getMbId());
            long result = bookService.rentBook(params);  // check point
             if(result == -1){
                message.setMessage("대여권수초과");
            }else{
                message.setMessage("대여완료");
            }

        } catch (Exception e) {
            // 예외 처리 (도서가 대여 불가능한 상태일 때의 처리 등)
            message.setMessage("대여실패");
        }
        return message;
    }

    // 도서 반납 처리 역할
    @PostMapping("/book/return")
    public String returnBook(HttpServletRequest request, final RentRequest params, Model model) {

        HttpSession session = request.getSession();
        MemberResponse member = (MemberResponse) session.getAttribute("loginMember");
        params.setMbId(member.getMbId());

        int result = bookService.returnBookToday(params);

        if (result >= 1){

            MessageDto message = new MessageDto("도서반납이 완료되었습니다.", "/book/list", RequestMethod.GET, null);

            return showMessageAndRedirect(message, model);

        }else{
            MessageDto message = new MessageDto("도서반납에 실패하였습니다.", "/book/list", RequestMethod.GET, null);

            return showMessageAndRedirect(message, model);
        }
    }
}

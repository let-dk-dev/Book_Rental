package com.dk.board_book.domain.book;

import com.dk.board_book.common.dto.SearchDto;
import com.dk.board_book.domain.rent.RentRequest;
import com.dk.board_book.domain.rent.RentResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    /**
     * 도서 저장
     *
     * @param params - 도서 정보
     */
    void save(BookRequest params);

    /**
     * 도서 상세정보 조회
     *
     * @param bookId - PK
     * @return 도서 상세정보
     */
    BookResponse findById(Long bookId);

    /**
     * 도서 수정
     *
     * @param params - 도서 정보
     */
    void update(BookRequest params);

    /**
     * 도서 삭제
     *
     * @param bookId - PK
     */
    void deleteById(Long bookId);

    /**
     * (전체 도서) 리스트 조회
     *
     * @return 도서 리스트
     */
    List<BookResponse> findAll(SearchDto params);

    /**
     * (대여 중인 도서) 리스트 조회
     *
     * @return 대여중인 도서 리스트
     */
    List<RentResponse> findAllRentedBooks (SearchDto params);

    /**
     * (전체 도서 수) 카운팅
     *
     * @return 도서 수
     */
    int count(SearchDto params);

    /**
     * (대여 중인 도서 수) 카운팅
     *
     * @return 도서 수
     */
    int countRentedBooks(SearchDto params);

    /**
     * 도서 대여
     *
     * @param params - 대여정보
     */
    Long rent(RentRequest params);

    /**
     * 도서가 대여가능한지 체크
     *
     * @return 대여정보
     */
//    boolean isRentAble(RentRequest params); // (option 1)
    boolean isRentAble(BookRequest params);  // (option 2)

    /**
     * 해당유저의 대여 도서 권수 카운팅
     *
     * @return 도서 수
     */
    int countRent(RentRequest params);

    /**
     * 도서의 대여가능여부 (불가)로 변경하는 역할
     *
     * @return 대여정보
     */
    int setRentalAvailableN(BookRequest params);

    /**
     * 도서의 대여가능여부 (가능)으로 변경하는 역할
     *
     * @return 대여정보
     */
    int setRentalAvailableY(BookRequest params);

    /**
     * 해당유저의 대여중인 도서 조회하는 역할
     *
     * @return 해당유저의 미반납도서목록
     */
    List<RentResponse> findRentedBooksByUserId(Long memberId);

    /**
     * 반납 처리하는 역할
     *
     * @return
     */
     int returnBookToday(RentRequest params);
}


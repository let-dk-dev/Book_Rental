package com.dk.board_book;

import com.dk.board_book.domain.book.BookMapper;
import com.dk.board_book.domain.rent.RentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookMapperTest {

    @Autowired
    BookMapper bookMapper;

    @Test
    void rent() {

        //given
        RentRequest rentInfo = new RentRequest();

         rentInfo.setBkId(3l);
         rentInfo.setMbId(3l);

        //when
        bookMapper.rent(rentInfo);

        //then
        System.out.println("테스트완료");
    }

    @Test
    void 유저의대여상태확인() {

            //given
            RentRequest rentInfo = new RentRequest();

//            rentInfo.setMbId(2l);
            Long memberId = 2l;

            //when
//            bookMapper.findRentedBooksByUserId(rentInfo);
            bookMapper.findRentedBooksByUserId(memberId);

            //then
            System.out.println("테스트완료1");
    }

    @Test
    void 반납() {

            //given
            RentRequest rentInfo = new RentRequest();

            rentInfo.setMbId(2l);
            rentInfo.setBkId(12l);

            //when
//            bookMapper.returnBook(rentInfo);
            bookMapper.returnBookToday(rentInfo);

            //then
            System.out.println("테스트완료2");
    }
}

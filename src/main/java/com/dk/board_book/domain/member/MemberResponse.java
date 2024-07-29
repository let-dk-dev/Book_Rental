package com.dk.board_book.domain.member;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MemberResponse {

    private Long mbId;                     // 회원 번호 (PK)
    private String loginId;                // 로그인 ID
    private String password;               // 비밀번호
    private String name;                   // 이름
    private Gender gender;                 // 성별
    private LocalDate birthday;            // 생년월일

    private String phone;                  // 휴대폰번호
    private String mainAddress;            // 큰주소(도로명주소)
    private String detailAddress;          // 디테일주소
    private String extraAddress;           // 주소-참고항목
    private char memberType;               //멤버타입 (0:일반회원, 1:관리자)

    private Boolean deleteYn;              // 삭제 여부
    private LocalDateTime createdDate;     // 생성일시
    private LocalDateTime modifiedDate;    // 최종 수정일시

    public void clearPassword() {

        this.password = "";
    }
}

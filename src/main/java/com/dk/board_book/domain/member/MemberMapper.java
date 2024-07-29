package com.dk.board_book.domain.member;

import com.dk.board_book.common.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    /**
     * 회원 정보 저장 (회원가입)
     * @param params - 회원 정보
     */
    void save(MemberRequest params);

    /**
     * 회원 상세정보 조회
     * @param loginId - UK
     * @return 회원 상세정보
     */
    MemberResponse findByLoginId(String loginId);

    /**
     * 회원 상세정보 조회
     * @param mbId - PK
     * @return 회원 상세정보
     */
    MemberResponse findBymbId(String mbId);


    /**
     * 회원 정보 수정
     * @param params - 회원 정보
     */
    void update(MemberRequest params);

    /**
     * 회원 정보 삭제 (회원 탈퇴)
     * @param mbId - PK
     */
    void deleteById(Long mbId);

    /**
     * 회원 수 카운팅 (ID 중복 체크)
     * @param loginId - UK
     * @return 회원 수
     */
    int countByLoginId(String loginId);

    /**
     * 멤버 수 카운팅
     *
     * @return 멤버 수
     */
    int count(SearchDto params);

    /**
     * 회원 리스트
     * @param member
     * @return 회원 리스트
     */
    List<MemberResponse> findAll(SearchDto member);
}

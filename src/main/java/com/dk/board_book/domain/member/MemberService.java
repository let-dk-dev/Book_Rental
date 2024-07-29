package com.dk.board_book.domain.member;

import com.dk.board_book.common.dto.SearchDto;
import com.dk.board_book.common.paging.Pagination;
import com.dk.board_book.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     * @param loginId - 로그인 ID
     * @param password - 비밀번호
     * @return 회원 상세정보
     */
    public MemberResponse login(final String loginId, final String password) {

        // 1. 회원 정보 및 비밀번호 조회
        MemberResponse member = findMemberByLoginId(loginId);

        String encodedPassword = (member == null) ? "" : member.getPassword();

        // 2. 회원 정보 및 비밀번호 체크
        if (member == null || passwordEncoder.matches(password, encodedPassword) == false) {
            return null;
        }

        // 3. 회원 응답 객체에서 비밀번호를 제거한 후 회원 정보 리턴
        member.clearPassword();
        return member;
    }

    /**
     * 회원 정보 저장 (회원가입)
     * @param params - 회원 정보
     * @return PK
     */
    @Transactional
    public Long saveMember(final MemberRequest params) {
        System.out.println("회원정보 저장 서비스");
        params.encodingPassword(passwordEncoder);

        memberMapper.save(params);

        return params.getMbId();
    }

    /**
     * 회원 상세정보 조회
     * @param loginId - UK
     * @return 회원 상세정보
     */
    public MemberResponse findMemberByLoginId(final String loginId) {

        return memberMapper.findByLoginId(loginId);
    }

    /**
     * 회원 상세정보 조회
     * @param mbId - PK
     * @return 회원 상세정보
     */
    public MemberResponse findMemberByMbId(final String mbId) {

        return memberMapper.findBymbId(mbId);
    }

    /**
     * 회원 정보 수정
     * @param params - 회원 정보
     * @return PK
     */
    @Transactional
    public Long updateMember(final MemberRequest params) {

        params.encodingPassword(passwordEncoder);

        memberMapper.update(params);

        return params.getMbId();
    }

    /**
     * 회원 정보 삭제 (회원 탈퇴)
     * @param mbId - PK
     * @return PK
     */
    @Transactional
    public Long deleteMemberById(final Long mbId) {

        memberMapper.deleteById(mbId);

        return mbId;
    }

    /**
     * 회원 수 카운팅 (ID 중복 체크)
     * @param loginId - UK
     * @return 회원 수
     */
    public int countMemberByLoginId(final String loginId) {

        return memberMapper.countByLoginId(loginId);
    }

     /**
     * 회원 리스트
     * @param params - search conditions
     * @return 회원 리스트
     */
    public PagingResponse<MemberResponse> findAllMembers(final SearchDto params) {

        // 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
        int count = memberMapper.count(params);

        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);

        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<MemberResponse> list = memberMapper.findAll(params);

        return new PagingResponse<>(list, pagination);
    }
}

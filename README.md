# [도서대여 프로젝트]
---

## 프로젝트 돌리기 위해서 해야 할 일
- db커넥션 정보 수정(local: mysql ==>> ec2 remote: mariaDB)
- DDL문 참고해서 테이블 만들어 놓아야 함
---

## 공통함수
- function.js에 화면 공통함수 선언함
---

## 유의해야할 점
- class WebMvcConfig에서 인터셉터가 적용될 pattern, 제외될 pattern 정의 시, 유의해야 함
- 첨부 파일 upload 관련 code는 (class FileUtils)에 있음
- 파일 첨부 시, 첨부한 날짜 별로 폴더가 자동 생성됨도 잘 check해야 함
---

## 개발후 아쉬운 점
- 테이블명, 컬럼명, 자바변수명, 자바스크립트변수명, html name명을 일괄적이고, 직관적으로 선언하지 못한 점
- 코드 재활용으로 기존 코드의 반복적인 부분이 있는 점


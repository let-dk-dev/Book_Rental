SHOW DATABASES;

SHOW tables;

USE board_book_db;

CREATE TABLE `board` (
    `board_id`            bigint(20)    NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `title`         varchar(100)  NOT NULL COMMENT '제목',
    `content`       varchar(3000) NOT NULL COMMENT '내용',
    `writer`        varchar(20)   NOT NULL COMMENT '작성자',
    `view_cnt`      int(11)       NOT NULL COMMENT '조회 수',
    `notice_yn`     tinyint(1)    NOT NULL COMMENT '공지글 여부',
    `delete_yn`     tinyint(1)    NOT NULL COMMENT '삭제 여부',
    `created_date`  datetime      NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `modified_date` datetime               DEFAULT NULL COMMENT '최종 수정일시',
    PRIMARY KEY (`board_id`)
) COMMENT '게시글';

DESC board;

SHOW tables;

SHOW FULL COLUMNS FROM board;

SELECT * FROM board;

SELECT count(*) FROM board;

TRUNCATE board;

INSERT INTO board (title, content, writer, view_cnt, notice_yn, delete_yn)
(SELECT title, content, writer, view_cnt, notice_yn, delete_yn FROM board WHERE delete_yn = 0);

create table re_comment (
      re_id bigint not null auto_increment comment '댓글 번호 (PK)'
    , bd_id bigint not null comment '게시글 번호 (FK)'
    , content varchar(1000) not null comment '내용'
    , writer varchar(20) not null comment '작성자'
    , delete_yn tinyint(1) not null comment '삭제 여부'
    , created_date datetime not null default CURRENT_TIMESTAMP comment '생성일시'
    , modified_date datetime comment '최종 수정일시'
    , primary key(re_id)
) comment '댓글';

SHOW FULL COLUMNS FROM re_comment;
DESC re_comment;

alter table re_comment
add constraint fk_re_comment foreign key(bd_id)
references board(board_id);

select *
from information_schema.table_constraints
where table_name = 're_comment';

SELECT * FROM re_comment;

INSERT INTO re_comment (
            bd_id, content, writer, delete_yn, created_date, modified_date
) VALUES (
              8069
            , '999번 댓글 37'
            , '홍길동'
            , 0
            , NOW()
            , NULL
);
-- ---------------------------------------------------------------------------

drop table `member`;

CREATE TABLE `member` (
  `mb_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '회원 번호 (PK)',
  `login_id` varchar(20) NOT NULL COMMENT '로그인 ID',
  `password` varchar(60) NOT NULL COMMENT '비밀번호',
  `name` varchar(20) NOT NULL COMMENT '이름',
  `gender` enum('M','F') COMMENT '성별',
  `birthday` date comment '생년월일',
  `phone` varchar(15) comment '폰번호',
  `delete_yn` tinyint(1) NOT NULL COMMENT '삭제 여부(0:정상, 1:탈퇴)' default 0,
  `main_address` varchar(300) COMMENT '메인주소',
  `detail_address` varchar(300) COMMENT '디테일주소',
  `member_type` char(1) COMMENT '회원유형(0:일반회원, 1:관리자)' default 0,
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `modified_date` datetime DEFAULT NULL COMMENT '최종 수정일시',
  PRIMARY KEY (`mb_id`),
  UNIQUE KEY uix_member_login_id (`login_id`)
) COMMENT '회원';

INSERT INTO board_book_db.member
(login_id, password, name, gender, birthday, phone, delete_yn, main_address, detail_address, member_type, created_date, modified_date)
VALUES('mem1', '1111', '길동', 'M', '2020-01-01', '010-1111-1111', 0, '서울', '신림', 0, current_timestamp(), current_timestamp());

INSERT INTO board_book_db.member
(login_id, password, name, gender, birthday, phone, delete_yn, main_address, detail_address, member_type, created_date, modified_date)
VALUES('mem3', '3333', '꺽정', 'M', '2020-01-03', '010-3333-3333', 0, '강원', '속초', 0, current_timestamp(), current_timestamp());

SELECT * FROM MEMBER;
-- ===========================================================================

drop table `file`;
CREATE TABLE `file` ( -- (version1)
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '파일 번호 (PK)',
  `bd_id` bigint(20) NOT NULL COMMENT '게시글 번호 (FK)',
  `original_name` varchar(255) NOT NULL COMMENT '원본 파일명',
  `save_name` varchar(40) NOT NULL COMMENT '저장 파일명',
  `size` int(11) NOT NULL COMMENT '파일 크기',
  `delete_yn` tinyint(1) NOT NULL COMMENT '삭제 여부',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `deleted_date` datetime DEFAULT NULL COMMENT '삭제일시',
  PRIMARY KEY (`file_id`),
  KEY `fk_bd_file` (`bd_id`),
  CONSTRAINT `fk_bd_file` FOREIGN KEY (`bd_id`) REFERENCES `board` (`board_id`)
) COMMENT '파일';

CREATE TABLE `file` ( -- (version2)
    `file_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '파일 번호 (PK)',
    `bd_id` bigint(20) NULL COMMENT '게시글 번호 (FK)',
    `bk_id` bigint(20) NULL COMMENT '책 번호 (FK)',
    `original_name` varchar(255) NOT NULL COMMENT '원본 파일명',
    `save_name` varchar(40) NOT NULL COMMENT '저장 파일명',
    `size` int(11) NOT NULL COMMENT '파일 크기',
    `delete_yn` tinyint(1) NOT NULL COMMENT '삭제 여부',
    `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `deleted_date` datetime DEFAULT NULL COMMENT '삭제일시',
    PRIMARY KEY (`file_id`),
    KEY `fk_bd_file` (`bd_id`),
    CONSTRAINT `fk_bd_file` FOREIGN KEY (`bd_id`) REFERENCES `board` (`board_id`),
    CONSTRAINT `fk_bk_thumbnail_file` FOREIGN KEY (`bk_id`) REFERENCES `book` (`book_id`)
  ) COMMENT '첨부파일';

CREATE TABLE `file` ( -- (version3)
	`file_key`	int	NOT NULL
	, `origin_filename`	varchar(200)	NOT NULL
	, `save_filename`	varchar(200)	NOT NULL
	, `ext`	varchar(10)	NOT NULL
	, `size`	int	NOT NULL default 0
	, `save_address`	varchar(300)	NOT NULL
	, `cr_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '생성일시'
	, `cr_memberid`	bigint		NOT null	COMMENT '작성자'
	, `md_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '수정일시'
	, `md_memberid`	bigint		NOT null COMMENT '수정자'
	, primary key(file_key)
);

SELECT * FROM file;
-- =================================================================================

drop table book;
CREATE TABLE `book` ( -- (version1)
	  `book_id`	bigint	auto_increment NOT NULL		COMMENT '자동시퀀스 1씩 증가'
	, `isbn`	varchar(17)	NOT NULL	COMMENT '3글자-2글자-4글자-3글자-1글자'
	, `book_name`	varchar(300)		comment '책제목'
	, `cover_file`	varchar(300) 	comment '표지파일'
	, `writer`	varchar(100)		comment '저자'
	, `pb_comp`	varchar(100)		comment '출판사'
	, `pb_date`	date				comment '출판일'
	, `price`		int	    			comment '가격'
	, `cr_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '생성일시'
	, `cr_memberid`	bigint		NOT null	COMMENT '작성자'
	, `md_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '수정일시'
	, `md_memberid`	bigint		NOT null COMMENT '수정자'
	, primary key(book_id)
);

CREATE TABLE `book` ( -- (version2)
	  `book_id`	bigint	auto_increment NOT NULL		COMMENT '자동시퀀스 1씩 증가'
	, `isbn`	varchar(17)	NOT NULL	COMMENT '3글자-2글자-4글자-3글자-1글자'
	, `book_name`	varchar(300)		comment '책제목'
	, `cover_file`	varchar(300) 	comment '표지파일'
	, `writer`	varchar(100)		comment '저자'
	, `pb_comp`	varchar(100)		comment '출판사'
	, `pb_date`	date				comment '출판일'
	, `price`		int	    			comment '가격'
	, `cr_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '생성일시'
	, `cr_memberid`	bigint		COMMENT '작성자'
	, `md_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '수정일시'
	, `md_memberid`	bigint		COMMENT '수정자'
	, primary key(book_id)
);

SELECT * FROM book;

DELETE FROM book;
-- ===============================================================================

-- drop table rental;
CREATE TABLE `rental` (
	  `rental_no`	bigint	auto_increment NOT null	COMMENT '대여ID'
	, `mb_id`	bigint	NOT NULL	COMMENT '대여자ID'
	, `bk_id`		bigint	NOT null	COMMENT '대여도서ID'
	, `rental_date`	datetime	NOT NULL	COMMENT '대여일. 반납계산일은 대여일+14'
	, `return_date`	datetime	NOT null	COMMENT '실제반납일'
	, `cr_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '생성일시'
	, `cr_memberid`	bigint		NOT null	COMMENT '작성자'
	, `md_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '수정일시'
	, `rental_availableyn`	char(1)	NOT NULL	DEFAULT 'Y'	COMMENT 'Y:대여가능 N:대여불가능'
	, `md_memberid`	bigint		NOT null COMMENT '수정자'
	, primary key(rental_no)
);

ALTER TABLE `rental` ADD CONSTRAINT `FK_member_TO_rental_1` FOREIGN KEY (`memberid`)
REFERENCES `member` (`mb_id`);

ALTER TABLE `rental` ADD CONSTRAINT `FK_book_TO_rental_1` FOREIGN KEY (`bookid`)
REFERENCES `book` (`book_id`);

-- (version2)
CREATE TABLE `rental` (
  `rental_no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '대여ID',
  `mb_id` bigint(20) NOT NULL COMMENT '대여자ID',
  `bk_id` bigint(20) NOT NULL COMMENT '대여도서ID',
  `rental_date` datetime NOT NULL COMMENT '대여일. 반납계산일은 대여일+14',
  `return_date` datetime DEFAULT NULL COMMENT '실제반납일',
  `cr_date` datetime DEFAULT current_timestamp() COMMENT '생성일시',
  `cr_memberid` bigint(20) DEFAULT NULL COMMENT '작성자',
  `md_date` datetime DEFAULT current_timestamp() COMMENT '수정일시',
  `md_memberid` bigint(20) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`rental_no`),
  KEY `FK_member_TO_rental_1` (`mb_id`),
  KEY `FK_book_TO_rental_1` (`bk_id`),
  CONSTRAINT `FK_book_TO_rental_1` FOREIGN KEY (`bk_id`) REFERENCES `book` (`book_id`),
  CONSTRAINT `FK_member_TO_rental_1` FOREIGN KEY (`mb_id`) REFERENCES `member` (`mb_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- ===============================================================================

/* 도서정보 저장 */
INSERT INTO board_book_db.book
(book_id, isbn, book_name, cover_file, writer
	, pb_comp, pb_date, price
	, cr_date, cr_memberid, md_date, md_memberid)
VALUES(0, '978-89-5674-724-2', '성공적인 웹 프로그래밍  PHP 와MySQL', NULL, '루크 웰링, 로라 톰슨'
	, '정보문화사', '2023-03-17', 32000
	, current_timestamp(), 0, current_timestamp(), 0);
;

/* 도서정보 저장 */
INSERT INTO board_book_db.book
(book_id, isbn, book_name, cover_file, writer
	, pb_comp, pb_date, price
	, cr_date, cr_memberid, md_date, md_memberid)
VALUES(0, '123-45-6789-001-1', '삼국지', NULL, '나관중'
	, '길벗', '2001-01-01', 12000
	, current_timestamp(), 0, current_timestamp(), 0);
;
SELECT * FROM book;

DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
	`review_id`	bigint	NOT NULL auto_increment comment '리뷰 번호 (PK)'
	, `writer`	bigint	NULL			comment '작성자'
	, `bk_id`	bigint	null    		comment '북ID'
	, `content`	varchar(2000)	null    comment '리뷰내용'
	, `delete_yn` tinyint(1) not null default 0 comment '삭제 여부'
	, `cr_date`	datetime	NULL	DEFAULT now()
	, `md_date`	datetime	NULL	DEFAULT now()
	, primary key(review_id)
);

ALTER TABLE `review` ADD CONSTRAINT  FOREIGN KEY (`writer`) REFERENCES `member` (`mb_id`);
ALTER TABLE `review` ADD CONSTRAINT  FOREIGN KEY (`bk_id`) REFERENCES `book` (`book_id`);

select * from file;
select * from review;

CREATE TABLE `tb_post` (
    `id`            bigint(20)    NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `title`         varchar(100)  NOT NULL COMMENT '제목',
    `content`       varchar(3000) NOT NULL COMMENT '내용',
    `writer`        varchar(20)   NOT NULL COMMENT '작성자',
    `view_cnt`      int(11)       NOT NULL COMMENT '조회 수',
    `notice_yn`     tinyint(1)    NOT NULL COMMENT '공지글 여부',
    `delete_yn`     tinyint(1)    NOT NULL COMMENT '삭제 여부',
    `created_date`  datetime      NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `modified_date` datetime               DEFAULT NULL COMMENT '최종 수정일시',
    PRIMARY KEY (`id`)
) COMMENT '게시글';


-- drop table `tb_member`;
CREATE TABLE `tb_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '회원 번호 (PK)',
  `login_id` varchar(20) NOT NULL COMMENT '로그인 ID',
  `password` varchar(60) NOT NULL COMMENT '비밀번호',
  `name` varchar(20) NOT NULL COMMENT '이름',
  `gender` enum('M','F') COMMENT '성별',
  `birthday` date comment '생년월일',
  `phone` varchar(15) comment '폰번호',
  `delete_yn` tinyint(1) NOT NULL COMMENT '삭제 여부(0:정상, 1:탈퇴)' default 0,
  `main_address` varchar(300) COMMENT '메인주소',
  `detail_address` varchar(300) COMMENT '디테일주소',
  `extra_address` varchar(300) COMMENT '참고항목-주소',
  `member_type` char(1) COMMENT '회원유형(0:일반회원, 1:관리자)' default 0,
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `modified_date` datetime DEFAULT NULL COMMENT '최종 수정일시',
  PRIMARY KEY (`id`),
  UNIQUE KEY uix_member_login_id (`login_id`)
) COMMENT '회원';



create table tb_comment (
      id bigint not null auto_increment comment '댓글 번호 (PK)'
    , post_id bigint not null comment '게시글 번호 (FK)'
    , content varchar(1000) not null comment '내용'
    , writer varchar(20) not null comment '작성자'
    , delete_yn tinyint(1) not null comment '삭제 여부'
    , created_date datetime not null default CURRENT_TIMESTAMP comment '생성일시'
    , modified_date datetime comment '최종 수정일시'
    , primary key(id)
) comment '댓글';



drop table TB_BOOK;
CREATE TABLE `TB_BOOK` (
	  `bookid`	bigint	auto_increment NOT NULL		COMMENT '자동시퀀스 1씩 증가'
	, `isbn`	varchar(18)	NOT NULL	COMMENT '4글자-2글자-4글자-3글자-1글자'
	, `book_name`	varchar(300)		comment '책제목'
	, `cover_file`	varchar(300) 	comment '표지파일'
	, `writer`	varchar(100)		comment '저자'
	, `pb_comp`	varchar(100)		comment '출판사'
	, `pb_date`	date				comment '출판일'
	, `price`		int	    		comment '가격'
    , `rental_availableyn` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y:대여가능 N:대여불가능'
	, `cr_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '생성일시'
	, `cr_memberid`	bigint	COMMENT '작성자'
	, `md_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '수정일시'
	, `md_memberid`	bigint	 COMMENT '수정자'
	, primary key(bookid)
);
-- ============================================================================

drop table TB_RENTAL;
-- (version 1)
CREATE TABLE `TB_RENTAL` (
	  `rental_no`	bigint	auto_increment NOT null	COMMENT '대여ID'
	, `memberid`	bigint	NOT NULL	COMMENT '대여자ID'
	, `bookid`		bigint	NOT null	COMMENT '대여도서ID'
	, `rental_date`	datetime	NOT NULL	COMMENT '대여일. 반납계산일은 대여일+14'
	, `return_date`	datetime	NOT null	COMMENT '실제반납일'
	, `cr_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '생성일시'
	, `cr_memberid`	bigint		NOT null	COMMENT '작성자'
	, `md_date`	datetime	NOT null DEFAULT current_timestamp() COMMENT '수정일시'
	, `rental_availableyn`	char(1)	NOT NULL	DEFAULT 'Y'	COMMENT 'Y:대여가능 N:대여불가능'
	, `md_memberid`	bigint		NOT null COMMENT '수정자'
	, primary key(rental_no)
);

ALTER TABLE `TB_RENTAL` ADD CONSTRAINT `FK_member_TO_rental_1` FOREIGN KEY (`memberid`)
REFERENCES `tb_member` (`id`);

ALTER TABLE `TB_RENTAL` ADD CONSTRAINT `FK_book_TO_rental_1` FOREIGN KEY (`bookid`)
REFERENCES `TB_BOOK` (`bookid`);
--------------------------------------------------------------------------------------

-- (version2)
CREATE TABLE `tb_rental` (
  `rental_no` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '대여ID',
  `memberid` bigint(20) NOT NULL COMMENT '대여자ID',
  `bookid` bigint(20) NOT NULL COMMENT '대여도서ID',
  `rental_date` datetime NOT NULL COMMENT '대여일. 반납계산일은 대여일+14',
  `return_date` datetime DEFAULT NULL COMMENT '실제반납일',
  `cr_date` datetime DEFAULT current_timestamp() COMMENT '생성일시',
  `cr_memberid` bigint(20) DEFAULT NULL COMMENT '작성자',
  `md_date` datetime DEFAULT current_timestamp() COMMENT '수정일시',
  `md_memberid` bigint(20) DEFAULT NULL COMMENT '수정자',
  PRIMARY KEY (`rental_no`),
  KEY `FK_member_TO_rental_1` (`memberid`),
  KEY `FK_book_TO_rental_1` (`bookid`),
  CONSTRAINT `FK_book_TO_rental_1` FOREIGN KEY (`bookid`) REFERENCES `tb_book` (`bookid`),
  CONSTRAINT `FK_member_TO_rental_1` FOREIGN KEY (`memberid`) REFERENCES `tb_member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-- =============================================================

drop table `tb_file`;
/*
CREATE TABLE `tb_file` (
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
*/
CREATE TABLE `tb_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '파일 번호 (PK)',
  `post_id` bigint(20) NULL COMMENT '게시글 번호 (FK)',
  `book_id` bigint(20) NULL COMMENT '책 번호 (FK)',
  `original_name` varchar(255) NOT NULL COMMENT '원본 파일명',
  `save_name` varchar(40) NOT NULL COMMENT '저장 파일명',
  `size` int(11) NOT NULL COMMENT '파일 크기',
  `delete_yn` tinyint(1) NOT NULL COMMENT '삭제 여부',
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `deleted_date` datetime DEFAULT NULL COMMENT '삭제일시',
  PRIMARY KEY (`id`),
  KEY `fk_post_file` (`post_id`),
  CONSTRAINT `fk_post_file` FOREIGN KEY (`post_id`) REFERENCES `tb_post` (`id`),
  CONSTRAINT `fk_post_thumbnail_file` FOREIGN KEY (`book_id`) REFERENCES `tb_book` (`bookid`)
) COMMENT '첨부파일';
-- ===========================================================================

-- DROP TABLE IF EXISTS `tb_review`;
CREATE TABLE `tb_review` (
	`reviewid`	bigint	NOT NULL auto_increment comment '리뷰 번호 (PK)'
	, `writer`	bigint	NULL			comment '작성자'
	, `bookid`	bigint	null    		comment '북ID'
	, `content`	varchar(2000)	null    comment '리뷰내용'
	, `delete_yn` tinyint(1) not null default 0 comment '삭제 여부'
	, `cr_date`	datetime	NULL	DEFAULT now()
	, `md_date`	datetime	NULL	DEFAULT now()
	, primary key(reviewid)
);

ALTER TABLE `tb_review` ADD CONSTRAINT  FOREIGN KEY (`writer`) REFERENCES `tb_member` (`id`);
ALTER TABLE `tb_review` ADD CONSTRAINT  FOREIGN KEY (`bookid`) REFERENCES `tb_book` (`bookid`);

select * from tb_member;
select * from tb_file;
select * from tb_review;


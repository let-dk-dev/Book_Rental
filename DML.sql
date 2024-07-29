/* 도서정보 저장 */
INSERT INTO board_book_db.book
(book_id, isbn, book_name, cover_file, writer
	, pb_comp, pb_date, price
	, cr_date, cr_memberid, md_date, md_memberid)
VALUES(0, '978-89-5674-724-2', '성공적인 웹 프로그래밍  PHP 와MySQL', NULL, '루크 웰링, 로라 톰슨'
	, '정보문화사', '2023-03-17', 32000
	, current_timestamp(), 0, current_timestamp(), 0);
;


select * from tb_book;
delete from tb_book;

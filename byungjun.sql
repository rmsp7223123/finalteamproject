insert into member(member_id, member_pw, member_name, member_nickname, member_age, member_gender, member_phone, member_admin)
values('admin', 'admin', 'admin', 'admin', '999', '남', '010-1234-5678', 1);

select * from member;

commit;

insert into option_table (member_id, option_font_size, option_font_color) values('admin', 20, '#000');

select * from option_table;

rollback;

insert into dday(member_id, dday_date, dday_content) values('admin', sysdate, '알람내용');

select * from dday;

rollback;

select member_profileimg from member where member_profileimg = 'afsdadsf';

commit;

select * from member where member_nickname = 'ansqudwns';

select * from member;

delete from member where member_name = '홍길동'
		and member_nickname = 'qrew' and member_id = 'qrew1'
		and member_pw = 'asdf123!';
        
SELECT 
    CU.TABLE_NAME AS "Foreign Table",
    CU.COLUMN_NAME AS "Foreign Column",
    PK.TABLE_NAME AS "Primary Table",
    PT.COLUMN_NAME AS "Primary Column"
FROM 
    USER_CONSTRAINTS C
    INNER JOIN USER_CONS_COLUMNS CU
        ON C.CONSTRAINT_NAME = CU.CONSTRAINT_NAME
    INNER JOIN USER_CONSTRAINTS PK
        ON C.R_CONSTRAINT_NAME = PK.CONSTRAINT_NAME
    INNER JOIN USER_CONS_COLUMNS PT
        ON PK.CONSTRAINT_NAME = PT.CONSTRAINT_NAME
WHERE C.CONSTRAINT_TYPE = 'R'
  AND CU.TABLE_NAME IN (
      'CALL', 'COMMON1', 'COMMON2', 'CSBOARD', 'CSBOARD_COMMENT', 'DDAY', 
      'EPHONE', 'FAV_BOARD', 'FAV_BOARD_COMMENT', 'FAV_BOARD_LIKE', 'FAVOR', 
      'GAME', 'LOCATION', 'MANNER', 'MEMBER', 'OPTION_TABLE', 'REGION', 
      'SENIOR', 'SENIOR_BMARK', 'SENIOR_LIKE', 'TABLE1', 'TEST'
  );
  
-- CSBOARD 테이블
ALTER TABLE CSBOARD DROP CONSTRAINT FK_MEMBER_TO_CSBOARD_1;
ALTER TABLE CSBOARD ADD CONSTRAINT FK_MEMBER_TO_CSBOARD_1 
FOREIGN KEY (WRITER) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- CSBOARD_COMMENT 테이블
ALTER TABLE CSBOARD_COMMENT DROP CONSTRAINT FK_MEMBER_TO_CSBOARD_COMMENT_1;
ALTER TABLE CSBOARD_COMMENT ADD CONSTRAINT FK_MEMBER_TO_CSBOARD_COMMENT_1 
FOREIGN KEY (WRITER) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- DDAY 테이블
ALTER TABLE DDAY DROP CONSTRAINT FK_MEMBER_TO_DDAY_1;
ALTER TABLE DDAY ADD CONSTRAINT FK_MEMBER_TO_DDAY_1 
FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- EPHONE 테이블
ALTER TABLE EPHONE DROP CONSTRAINT FK_MEMBER_TO_EPHONE_1;
ALTER TABLE EPHONE ADD CONSTRAINT FK_MEMBER_TO_EPHONE_1 
FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- FAVOR 테이블
ALTER TABLE FAVOR DROP CONSTRAINT FK_MEMBER_TO_FAVOR_1;
ALTER TABLE FAVOR ADD CONSTRAINT FK_MEMBER_TO_FAVOR_1 
FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- FAV_BOARD 테이블
ALTER TABLE FAV_BOARD DROP CONSTRAINT FK_MEMBER_TO_FAV_BOARD_1;
ALTER TABLE FAV_BOARD ADD CONSTRAINT FK_MEMBER_TO_FAV_BOARD_1 
FOREIGN KEY (WRITER) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- FAV_BOARD_COMMENT 테이블
ALTER TABLE FAV_BOARD_COMMENT DROP CONSTRAINT FK_MEMBER_TO_FAV_BOARD_COMMENT_1;
ALTER TABLE FAV_BOARD_COMMENT ADD CONSTRAINT FK_MEMBER_TO_FAV_BOARD_COMMENT_1 
FOREIGN KEY (WRITER) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;
ALTER TABLE FAV_BOARD_COMMENT DROP CONSTRAINT FK_FAV_BOARD_TO_FAV_BOARD_COMMENT_1;
ALTER TABLE FAV_BOARD_COMMENT ADD CONSTRAINT FK_FAV_BOARD_TO_FAV_BOARD_COMMENT_1 
FOREIGN KEY (FAV_BOARD_ID) REFERENCES FAV_BOARD (FAV_BOARD_ID) ON DELETE CASCADE;

-- FAV_BOARD_LIKE 테이블
ALTER TABLE FAV_BOARD_LIKE DROP CONSTRAINT FK_MEMBER_TO_FAV_BOARD_LIKE_1;
ALTER TABLE FAV_BOARD_LIKE ADD CONSTRAINT FK_MEMBER_TO_FAV_BOARD_LIKE_1 
FOREIGN KEY (MEMBER_ID_LIKE) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;
ALTER TABLE FAV_BOARD_LIKE DROP CONSTRAINT FK_FAV_BOARD_TO_FAV_BOARD_LIKE_1;
ALTER TABLE FAV_BOARD_LIKE ADD CONSTRAINT FK_FAV_BOARD_TO_FAV_BOARD_LIKE_1 
FOREIGN KEY (FAV_BOARD_ID) REFERENCES FAV_BOARD (FAV_BOARD_ID) ON DELETE CASCADE;

-- GAME 테이블
ALTER TABLE GAME DROP CONSTRAINT FK_MEMBER_TO_GAME_1;
ALTER TABLE GAME ADD CONSTRAINT FK_MEMBER_TO_GAME_1 
FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- LOCATION 테이블
ALTER TABLE LOCATION DROP CONSTRAINT FK_MEMBER_TO_LOCATION_1;
ALTER TABLE LOCATION ADD CONSTRAINT FK_MEMBER_TO_LOCATION_1 
FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- MANNER 테이블
ALTER TABLE MANNER DROP CONSTRAINT FK_MEMBER_TO_MANNER_1;
ALTER TABLE MANNER ADD CONSTRAINT FK_MEMBER_TO_MANNER_1 
FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- OPTION_TABLE 테이블
ALTER TABLE OPTION_TABLE DROP CONSTRAINT FK_MEMBER_TO_OPTION_TABLE_1;
ALTER TABLE OPTION_TABLE ADD CONSTRAINT FK_MEMBER_TO_OPTION_TABLE_1 
FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- REGION 테이블
ALTER TABLE REGION DROP CONSTRAINT FK_MEMBER_TO_REGION_1;
ALTER TABLE REGION ADD CONSTRAINT FK_MEMBER_TO_REGION_1 
FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

-- SENIOR_LIKE 테이블
ALTER TABLE SENIOR_LIKE DROP CONSTRAINT FK_MEMBER_TO_SENIOR_LIKE_1;
ALTER TABLE SENIOR_LIKE ADD CONSTRAINT FK_MEMBER_TO_SENIOR_LIKE_1 
FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID) ON DELETE CASCADE;

commit;

SELECT FAVOR.*, MEMBER.MEMBER_PROFILEIMG
FROM FAVOR
JOIN MEMBER ON FAVOR.MEMBER_ID = MEMBER.MEMBER_ID
WHERE FAVOR.MEMBER_ID = 'test';

select * from member;
DELETE FROM FAVOR;


COMMIT;
INSERT INTO FAVOR
SELECT 5 , member_id 
FROM MEMBER
WHERE ROWNUM BETWEEN 10 AND 18;
;




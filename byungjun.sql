insert into member(member_id, member_pw, member_name, member_nickname, member_age, member_gender, member_phone, member_admin)
values('admin', 'admin', 'admin', 'admin', '999', '남', '010-1234-5678', 1);

select * from member;

commit;

insert into option_table (member_id, option_font_size, option_font_color) values('admin', 20, '#000');

select * from option_table;

rollback;

insert into dday(member_id, dday_date, dday_content) values('admin', sysdate, '알람내용');

select * from member;

rollback;

select member_profileimg from member where member_profileimg = 'afsdadsf';

commit;

select * from member where member_nickname = 'ansqudwns';

select * from member;

select * from alarm;

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

insert into option_table (option_lock_pw, member_id) values ('1234', 'ansquwdns9811');

select * from option_table;

SELECT M.*
		FROM ( SELECT
		MEMBER_ID
		FROM FAVOR
		WHERE MEMBER_ID != '123qwe'
		AND
		FAVOR IN ( SELECT
		FAVOR
		FROM FAVOR
		WHERE MEMBER_ID='123qwe'
		)
		GROUP BY
		MEMBER_ID ) F
		INNER JOIN MEMBER M ON F.MEMBER_ID = M.MEMBER_ID;
        
        commit;
        
CREATE TABLE FRIEND_LIST(
    member_id nvarchar2(30) constraint friend_pk primary key ,
    friend_id nvarchar2(30),
    constraint friend_member_fk foreign key(member_id) references member(member_id) on delete cascade,
    constraint friend_friend_fk foreign key(friend_id) references member(member_id) on delete cascade
);

create table alarm(
    member_id nvarchar2(30),
    alarm_id number constraint alarm_pk primary key,
    alarm_content nvarchar2(1000),
    alarm_time nvarchar2(100),
    receive_id nvarchar2(30),
    constraint alarm_member_fk foreign key(member_id) references member(member_id) on delete cascade
);
create sequence seq_alarm
minvalue 1 
maxvalue 999999
increment by 1
start with 1
nocache;

CREATE OR REPLACE TRIGGER trg_alarm_id
BEFORE INSERT ON alarm
FOR EACH ROW
BEGIN
    :NEW.alarm_id := seq_alarm.NEXTVAL;
END;
/

commit;

drop trigger trg_alarm_id;
drop sequence seq_alarm;
drop table alarm;

commit;

rollback;

-- create or replace 

insert into alarm (member_id,alarm_content, alarm_time) values ('ansqudwns98','알람내용12', '시간1234');

drop table alarm;

select  * from alarm;

select * from member;

rollback;

CREATE OR REPLACE TRIGGER trg_insert_option
AFTER INSERT ON MEMBER
FOR EACH ROW
BEGIN
   INSERT INTO OPTION_TABLE (
      MEMBER_ID,
      OPTION_FONT_SIZE,
      OPTION_FONT_COLOR,
      OPTION_ALARM,
      OPTION_LOCK_PW,
      OPTION_GODOK_ALARM
   ) VALUES (
      :NEW.MEMBER_ID,
      '크게',
      '#000000',
      'Y', 
      NULL,
      'Y'
   );
END;
/

commit;

insert into Alarm (member_id, alarm_content, alarm_time) values ('ansqudwns98', 'content1', 'time1');

select * from alarm;

delete alarm where member_id = 'alaa1';

delete alarm;

rollback;

commit;

select alarm_content, alarm_time from alarm where member_id = 'ansqudwns98';


select alarm_content, alarm_time from alarm where receive_id = 'alaa1';

select * from friend_list where member_id = 'alaa1' ;--or FRIEND_ID = 'alaa1;

INSERT INTO friend_list values ( 'alarm1' , 'admin' );

delete from friend_list;

commit;
insert all 
	into friend_list(MEMBER_ID, FRIEND_ID) values ('alarm1', 'admin')
	into friend_list(MEMBER_ID, FRIEND_ID) values ('admin', 'alarm1')
select * from dual;

select * from friend_list;

select * from alarm;
commit;

select count(alarm_content) as content_cnt from alarm where member_id = '000a';

select count(alarm_content) as content_cnt from alarm where receive_id = '00000a';

select * from alarm where receive_id = '00000a';

select * from option_table where member_id = '00000a';

UPDATE option_table
		SET option_alarm = CASE
		WHEN option_alarm = 'N' THEN 'Y'
		WHEN option_alarm = 'Y' THEN 'N'
		END
		WHERE member_id = '00000a';        
        select * from option_table where member_id = 'aaatest1';
select * from alarm;
select * from option_table;

delete alarm where receive_id = '00000a' AND alarm_content LIKE '000001' AND alarm_content LIKE '메시지를';
delete alarm where receive_id = 'aaatest1' AND alarm_content LIKE '%000001%' AND alarm_content LIKE '%메시지를%';
select * from alarm;

commit;
rollback;

DELETE FROM alarm WHERE receive_id = 'aaatest1' AND alarm_content LIKE CONCAT('%', '000001', '%') AND alarm_content LIKE CONCAT('%', '메시지를', '%');

select * from friend_list;

select * from member where
		member_id = 'ansqudwns98' and member_pw = '123qwe!@';
        
        select * from member;
        
        
select * from alarm;

select * from location;

select * from member where member_id = 'testaaa1';

commit;

select * from location where member_id = 'ansqudwns98';

commit;
create table godok_alarm(
    alarm_id number primary key,
    member_id nvarchar2(30) not null,
    member_name nvarchar2(30) not null,
    alarm_time date not null,
    ephone_name nvarchar2(100) not null,
    ephone_phone nvarchar2(20) not null
);

create sequence seq_godok_alarm
minvalue 1 
maxvalue 999999
increment by 1
start with 1
nocache;

CREATE OR REPLACE TRIGGER trigger_alarm_id
BEFORE INSERT ON godok_alarm
FOR EACH ROW
BEGIN
    :NEW.alarm_id := seq_godok_alarm.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER trigger_insert_ephone_data
BEFORE INSERT ON godok_alarm
FOR EACH ROW
DECLARE
    ephone_name_var nvarchar2(100);
    ephone_phone_var nvarchar2(20);
BEGIN
    SELECT ephone_name, ephone_phone
    INTO ephone_name_var, ephone_phone_var
    FROM ephone
    WHERE member_id = :NEW.member_id;
    
    :NEW.ephone_name := ephone_name_var;
    :NEW.ephone_phone := ephone_phone_var;
END;
/

CREATE OR REPLACE TRIGGER trigger_insert_member_name
BEFORE INSERT ON godok_alarm
FOR EACH ROW
DECLARE
    member_name_var nvarchar2(30);
BEGIN
    SELECT member_name
    INTO member_name_var
    FROM member
    WHERE member_id = :NEW.member_id;
    :NEW.member_name := member_name_var;
END;
/


CREATE OR REPLACE TRIGGER trigger_delete_godok_alarm
AFTER DELETE ON member
FOR EACH ROW
BEGIN
    DELETE FROM godok_alarm
    WHERE member_id = :OLD.member_id;
END;
/
commit;

select * from godok_alarm;
insert into godok_alarm(member_id, alarm_time) values('00000a', sysdate);
insert into godok_alarm(member_id, alarm_time) values('ansqudwns98', sysdate);
select * from location;

SELECT *
FROM location
WHERE location_time <= SYSDATE - 3;
insert into location(member_id, location_latitude, location_longitude, location_time) values('00000a', '33', '33', TO_DATE('2023-08-21 13:30:20', 'YYYY-MM-DD HH24:MI:SS'));

select * from member;

select alarm_time from godok_alarm;

select * from godok_alarm;

commit;

insert into godok_alarm(member_id, alarm_time) values ('ansqudwns98', TO_DATE(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'));
insert into godok_alarm(member_id, alarm_time) values ('test111', sysdate);
insert into godok_alarm(member_id, alarm_time) values ('testest1', TO_DATE('2023-08-4 15:30:00', 'YYYY-MM-DD HH24:MI:SS'));

commit;

select * from godok_alarm ;

select * from member;

select * from ephone;

select to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') from dual;

select * from godok_alarm;

select * from godok_alarm order by alarm_id desc;

SELECT member_id, TO_CHAR(alarm_time, 'YYYY-MM-DD HH24:MI:SS') AS alarm_time
FROM godok_alarm;

commit;

select * from ephone;
select * from godok_alarm;
select * from member;




select member_id, member_name, alarm_time, ephone_name, ephone_phone, alarm_id, no from  
		(select row_number() over(order by alarm_id) no, g.*
		from godok_alarm g) n
where  member_id in ( select MEMBER_ID FROM MEMBER WHERE MEMBER_ADMIN = 0 )
and (member_id   like '%' || '홍길동' || '%' 
	   or member_name like '%' ||'홍길동' || '%' 
	   );
       
select * from ephone where member_id = 'testaaa1';

select * from location;

select * from location;

SELECT *
FROM location
WHERE location_time BETWEEN SYSDATE - INTERVAL '3' DAY + INTERVAL '9' HOUR AND SYSDATE - INTERVAL '3' DAY + INTERVAL '10' HOUR;

insert into location(member_id,LOCATION_LATITUDE,LOCATION_LONGITUDE,location_time) values('test1', '33','33', TO_DATE('2023-08-22 13:40:20', 'YYYY-MM-DD HH24:MI:SS'));

update location set location_time = TO_DATE('2023-08-25 13:40:20', 'YYYY-MM-DD HH24:MI:SS') where member_id = 'test2';

select * from location;
select * from godok_alarm;
commit;

select * from godok_alarm;

select * from location;
select * from member;

SELECT TO_CHAR(location_time, 'YYYY-MM-DD HH24:MI:SS') AS location_time
FROM location;

select to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS')
from dual;
select sysdate from dual;



SELECT *
		FROM location
		WHERE location_time BETWEEN SYSDATE - INTERVAL '3' DAY + INTERVAL '9' HOUR
		AND SYSDATE - INTERVAL '3' DAY + INTERVAL '10' HOUR;
    
    
    update location
    set location_time = sysdate + INTERVAL '9' HOUR
    WHERE location_time BETWEEN SYSDATE - INTERVAL '3' DAY + INTERVAL '9' HOUR
	AND SYSDATE - INTERVAL '3' DAY + INTERVAL '10' HOUR;
    
    select * from location;


rollback;

delete member;

select * from member;

--insert into member(member_id, member_pw, member_name, member_nickname, member_birth, member_gender, member_phone) values

select * from option_table;

select * from location;

commit;

select * from godok_alarm;

commit;

select count(member_admin) cnt from member where
		member_id = 'admin' and member_pw = 'admin' and member_admin = 1;

SELECT *
FROM location l
INNER JOIN ephone e ON l.member_id = e.member_id
INNER JOIN option_table o ON l.member_id = o.member_id
WHERE l.location_time BETWEEN SYSDATE - INTERVAL '3' DAY + INTERVAL '9' HOUR
                         AND SYSDATE - INTERVAL '3' DAY + INTERVAL '10' HOUR;

commit;

select * from option_table where member_id = 'test2';


commit;

select * from option_table;

select * from member;

select * from godok_alarm;

commit;
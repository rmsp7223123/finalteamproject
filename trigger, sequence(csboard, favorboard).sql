DROP TABLE COMMON2;
  CREATE TABLE COMMON2 
   (	KEY1 NUMBER, 
	KEY2 NUMBER, 
	NAME NVARCHAR2(20) 
   ) 


insert into member (MEMBER_ID, MEMBER_PW, MEMBER_NAME, MEMBER_NICKNAME, MEMBER_AGE, MEMBER_GENDER, MEMBER_PHONE) 
values ('test2', 'test', 'test2', 'test', 100, '巢', '010-1234-5678');

insert into common1 (KEY1, NAME)
values (1, '包缴荤');

insert into common1 (KEY1, NAME)
values (2, '林家');

insert into common2 (KEY1, KEY2, NAME)
values (2, 1, '堡林');

insert into common2 (KEY1, KEY2, NAME)
values (1, 1, 'TV');

insert into common2 (KEY1, KEY2, NAME)
values (1, 2, '澜厩');


COMMIT;



INSERT INTO FAVOR VALUES (1, 'test');

insert into region values (1, 'test', '辑备 丑己悼');

insert into csboard_comment values 
('2', '3', 'test', '1', sysdate);

insert into fav_board values
(1, 'test', 1, '1', '1', sysdate, 0);

insert into fav_board_comment values
(2, 1, 'test', '1', sysdate);

insert into fav_board_like values
('test', 456);

create or replace trigger trg_csboard_viewcount
before insert
on csboard
for each row
begin
    :new.csboard_viewcount := :old.csboard_viewcount+1;
end;

create sequence seq_csboard_id increment by 1;

create or replace trigger trg_csboard_viewcount
before insert
on csboard
for each row
begin
    :new.csboard_id := seq_csboard_id.nextval;
end;

create sequence seq_csboard_comment_id increment by 1;


create or replace trigger trg_csboard_comment_id
before insert
on csboard_comment
for each row
begin
    :new.csboard_comment_id := seq_csboard_comment_id.nextval;
end;


select * from manner;
insert into manner values ('test', 'test2', 80);

select avg(manner_score)
from manner 
where manner_opponent_id = 'test';





create or replace trigger trg_fav_board_writecount
before insert
on fav_board
for each row
begin 
    :new.fav_board_writecount := :old.fav_board_writecount+1;
end;

create sequence seq_fav_board_id increment by 1;

create or replace trigger trg_fav_board_id
before insert
on fav_board
for each row
begin 
    :new.fav_board_id := seq_fav_board_id.nextval;
end;

create sequence seq_fav_board_comment_id increment by 1;

create or replace trigger trg_fav_board_comment_id 
before insert
on fav_board_comment
for each row
begin
    :new.fav_board_comment_id := seq_fav_board_comment_id.nextval;
end;
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

commit;


--��������
--game���̺� rank�÷� pk����, null�����.

select * from game;

insert into game(MEMBER_ID, GAME_SCORE)
values('admin', 10);

update game
set MEMBER_ID='admin', GAME_SCORE=9;

commit;
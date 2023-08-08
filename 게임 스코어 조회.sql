--수정사항
--game테이블에 rank컬럼 pk삭제, null허용함.

select * from game;

insert into game(MEMBER_ID, GAME_SCORE)
values('admin', 10);

update game
set MEMBER_ID='admin', GAME_SCORE=9;

commit;
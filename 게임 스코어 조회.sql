--수정사항
--game테이블에 rank컬럼 pk삭제, null허용함.

select * from game;

insert into game(MEMBER_ID, GAME_SCORE)
values('test', 8);

update game
set MEMBER_ID='admin', GAME_SCORE=10
where MEMBER_ID='admin';

commit;
rollback;

update game
set MEMBER_ID='admin', GAME_SCORE=10
where MEMBER_ID='admin' and game_score > (select game_score -1
from game
where member_id='admin');

select game_score -1
from game
where member_id='admin';






--수정사항
--game테이블에 rank컬럼 pk삭제, null허용함.

select * from game
order by game_score desc;

insert into game(MEMBER_ID, GAME_SCORE)
values('test', 8);

update game
set MEMBER_ID='admin', GAME_SCORE=10
where MEMBER_ID='admin';

commit;
rollback;

update game
set MEMBER_ID='admin', GAME_SCORE=3
where MEMBER_ID='admin' and game_score >= (select game_score -1
from game
where member_id='admin');

select game_score
from game
where member_id='admin';

UPDATE game
SET GAME_SCORE = 10
WHERE MEMBER_ID = 'admin' AND GAME_SCORE > (
    SELECT GAME_SCORE
    FROM game
    WHERE MEMBER_ID = 'admin'
);


--점수 업데이트
UPDATE GAME
SET GAME_SCORE = 
CASE WHEN
(SELECT GAME_SCORE
    FROM game
    WHERE MEMBER_ID = 'test') < 15 THEN 15
ELSE (SELECT GAME_SCORE
    FROM game
    WHERE MEMBER_ID = 'test')
    END
WHERE MEMBER_ID = 'test'
    ;

DELETE 
  FROM game
 WHERE MEMBER_ID = 'conan1';




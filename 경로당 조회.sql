--https://www.baraverkstad.se/encode/
--특수문자 변환 사이트

--데이터가 너무 많아서 일부만 우선 보이게 작업 중
select * from senior where key between 2040 and 2440;

--경로당 이름으로 조회시 중복값 있음
select * from senior
where senior_name = '금성경로당';

select * from senior_like;

--좋아요 전체 조회
select s.SENIOR_NAME, l.SENIOR_LIKE_NUM
from SENIOR s
left join SENIOR_LIKE l
on s.key = l.key;

--좋아요 1건 조회
select SENIOR_LIKE_NUM
from (select s.SENIOR_NAME, l.SENIOR_LIKE_NUM
from SENIOR s
left join SENIOR_LIKE l
on s.key = l.key
where s.key=126);

--mapper
--전체 경로당 리스트 + 좋아요
select s.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS, s.SENIOR_NUMADDRESS, s.SENIOR_CALL, s.SENIOR_LATITUDE, s.SENIOR_LONGITUDE, s.SIDO, s.SIGUNGU
, l.SENIOR_LIKE_NUM
from SENIOR s
left join SENIOR_LIKE l
on s.key = l.key
where s.key < 25
order by key;

--자주가는 경로당(즐겨찾기) 조회
select * from SENIOR_BMARK
where member_id='admin';

--조회 쿼리 mapper
select b.member_id, b.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS
from senior_bmark b
left join senior s
on b.key=s.key
where member_id='test';


--mapper
--좋아요 누름
--자주가는 경로당(즐겨찾기) 추가
insert into SENIOR_BMARK (KEY, MEMBER_ID)
values(2, 'conan1');

--좋아요 테이블에 컬럼 생성
insert into SENIOR_LIKE (MEMBER_ID, KEY, SENIOR_LIKE_NUM)
values ('admin', 1, 0);
--컬럼은 관리자로만 생성

--카운트 +1
UPDATE SENIOR_LIKE
SET SENIOR_LIKE_NUM = SENIOR_LIKE_NUM + 1
WHERE KEY = 6910;


--좋아요 취소
--자주가는 경로당 삭제
delete from SENIOR_BMARK
where key = 20 and member_id='admin';

--카운트 -1
UPDATE SENIOR_LIKE
SET SENIOR_LIKE_NUM = SENIOR_LIKE_NUM - 1
WHERE KEY = 6910;


select key
from SENIOR_BMARK
where key=15 and member_id='admin';

select * from senior where key = 1;


--데이터 조회
select * from SENIOR_LIKE;
select * from SENIOR_BMARK;
select * from senior where SENIOR_ROADADDRESS like '%광주광역시%';

select * from senior where key=12;


rollback;
commit;
    
    


--화면에 보이는 지도 위 정보만 표현하기

SELECT  CASE WHEN DISTANCE < 1 THEN RTRIM(TO_CHAR(DISTANCE*1000 , 'FM9990D99'),'.')||'m' ELSE DISTANCE||'km' END distance,T.*
        , S.* ,  (SELECT COUNT(*) FROM SENIOR_LIKE L WHERE L.KEY = S.KEY ) SENIOR_LIKE_NUM
    from 
     (
        SELECT ROWNUM no, C.DISTANCE , S.KEY 
        FROM SENIOR S LEFT OUTER JOIN 
        (SELECT  KEY , DISTNACE_WGS84(35.1535583, 126.8879957, SENIOR_LATITUDE, SENIOR_LONGITUDE) AS DISTANCE
           FROM SENIOR S
      
        ) C ON S.KEY=C.KEY
        WHERE S.SENIOR_ROADADDRESS LIKE '%광주%'
        ORDER BY C.DISTANCE ASC
    ) T INNER JOIN SENIOR S ON T.KEY = S.KEY
    WHERE T.NO < 20 ;

--수정 1
SELECT  CASE WHEN DISTANCE < 1 THEN RTRIM(TO_CHAR(DISTANCE*1000 , 'FM9990D99'),'.')||'m' ELSE DISTANCE||'km' END distance,T.*
        , S.* ,  (SELECT COUNT(*) FROM SENIOR_BMARK B WHERE B.KEY = S.KEY ) SENIOR_LIKE_NUM
    from 
     (
        SELECT ROWNUM no, C.DISTANCE , S.KEY 
        FROM SENIOR S LEFT OUTER JOIN 
        (SELECT  KEY , DISTNACE_WGS84(35.1535583, 126.8879957, SENIOR_LATITUDE, SENIOR_LONGITUDE) AS DISTANCE
           FROM SENIOR S
      
        ) C ON S.KEY=C.KEY
        WHERE S.SENIOR_ROADADDRESS LIKE '%광주%'
        ORDER BY C.DISTANCE ASC
    ) T INNER JOIN SENIOR S ON T.KEY = S.KEY
    WHERE T.NO < 20 ;

select * from senior;


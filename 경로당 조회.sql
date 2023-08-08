--https://www.baraverkstad.se/encode/
--특수문자 변환 사이트

--데이터가 너무 많아서 25개만 우선 보이게 작업 중
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

--전체 경로당 리스트 + 좋아요
select s.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS, s.SENIOR_NUMADDRESS, s.SENIOR_CALL, s.SENIOR_LATITUDE, s.SENIOR_LONGITUDE, s.SIDO, s.SIGUNGU
, l.SENIOR_LIKE_NUM
from SENIOR s
left join SENIOR_LIKE l
on s.key = l.key
where s.key < 25
order by key;

--좋아요 테스트 쿼리
insert into SENIOR_LIKE (MEMBER_ID, KEY, SENIOR_LIKE_NUM)
values ('admin', 13, 1);
commit;

--자주가는 경로당 조회
select * from SENIOR_LIKE
where member_id='admin';

select l.member_id, l.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS
from senior_like l
left join senior s
on l.key=s.key
where member_id='admin';


















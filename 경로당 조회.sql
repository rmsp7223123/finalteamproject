--https://www.baraverkstad.se/encode/
--Ư������ ��ȯ ����Ʈ

--�����Ͱ� �ʹ� ���Ƽ� �Ϻθ� �켱 ���̰� �۾� ��
select * from senior where key between 2040 and 2440;

--��δ� �̸����� ��ȸ�� �ߺ��� ����
select * from senior
where senior_name = '�ݼ���δ�';

select * from senior_like;

--���ƿ� ��ü ��ȸ
select s.SENIOR_NAME, l.SENIOR_LIKE_NUM
from SENIOR s
left join SENIOR_LIKE l
on s.key = l.key;

--���ƿ� 1�� ��ȸ
select SENIOR_LIKE_NUM
from (select s.SENIOR_NAME, l.SENIOR_LIKE_NUM
from SENIOR s
left join SENIOR_LIKE l
on s.key = l.key
where s.key=126);

--mapper
--��ü ��δ� ����Ʈ + ���ƿ�
select s.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS, s.SENIOR_NUMADDRESS, s.SENIOR_CALL, s.SENIOR_LATITUDE, s.SENIOR_LONGITUDE, s.SIDO, s.SIGUNGU
, l.SENIOR_LIKE_NUM
from SENIOR s
left join SENIOR_LIKE l
on s.key = l.key
where s.key < 25
order by key;

--���ְ��� ��δ�(���ã��) ��ȸ
select * from SENIOR_BMARK
where member_id='admin';

--��ȸ ���� mapper
select b.member_id, b.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS, s.SENIOR_LATITUDE, s.SENIOR_LONGITUDE
from senior_bmark b
left join senior s
on b.key=s.key
where member_id='test';

--����
select b.member_id, b.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS, s.SENIOR_LATITUDE, s.SENIOR_LONGITUDE, l.SENIOR_LIKE_NUM
from senior_bmark b, senior s, SENIOR_LIKE l
where b.key=s.key and s.key=l.key and b.member_id='test'
;



select * from senior where key = 6978;
--mapper
--���ƿ� ��ư���
select * from senior_like
WHERE KEY=96;


DECLARE
    like_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO like_count
    FROM SENIOR_LIKE
    WHERE KEY = 96;

    IF like_count = 0 THEN
        INSERT INTO SENIOR_LIKE (MEMBER_ID, KEY, SENIOR_LIKE_NUM)
        VALUES ('admin', 96, 1);
        --�÷��� �����ڷθ� ����
    ELSE
        UPDATE SENIOR_LIKE
        SET SENIOR_LIKE_NUM = SENIOR_LIKE_NUM + 1
        WHERE KEY = 96;
    END IF;
    COMMIT;
    END;
/
    
    
--    
--    DBMS_OUTPUT.PUT_LINE('�۾��� �Ϸ�Ǿ����ϴ�.');
--EXCEPTION
--    WHEN OTHERS THEN
--        DBMS_OUTPUT.PUT_LINE('���� �߻�: ' || SQLERRM);
--        ROLLBACK;
--END;
--/

SELECT * FROM SENIOR_LIKE;

MERGE INTO SENIOR_LIKE L
USING DUAL
ON (L.KEY = 20)
WHEN MATCHED THEN
    UPDATE
    SET L.SENIOR_LIKE_NUM = L.SENIOR_LIKE_NUM + 1
WHEN NOT MATCHED THEN
    INSERT (MEMBER_ID, KEY, SENIOR_LIKE_NUM)
    VALUES ('admin', 20, 1);


--���ƿ� ����
--���ְ��� ��δ�(���ã��) �߰�
insert into SENIOR_BMARK (KEY, MEMBER_ID)
values(2, 'conan1');

--���ƿ� ���̺� �÷� ����
insert into SENIOR_LIKE (MEMBER_ID, KEY, SENIOR_LIKE_NUM)
values ('admin', 1, 0);
--�÷��� �����ڷθ� ����

--ī��Ʈ +1
UPDATE SENIOR_LIKE
SET SENIOR_LIKE_NUM = SENIOR_LIKE_NUM + 1
WHERE KEY = 2;


--���ƿ� ���
--���ְ��� ��δ� ����
delete from SENIOR_BMARK
where key = 20 and member_id='admin';

--ī��Ʈ -1
UPDATE SENIOR_LIKE
SET SENIOR_LIKE_NUM = SENIOR_LIKE_NUM - 1
WHERE KEY = 6910;


select key
from SENIOR_BMARK
where key=15 and member_id='admin';

select * from senior where key = 1;


--������ ��ȸ
select * from SENIOR_LIKE;
select * from SENIOR_BMARK;
select * from senior where SENIOR_ROADADDRESS like '%���ֱ�����%';

select * from senior where key=12;

select s.*, l.senior_like_num
from senior s, senior_like l
where s.key = l.key and s.key=6911;


select b.member_id, b.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS, s.SENIOR_LATITUDE, s.SENIOR_LONGITUDE, l.SENIOR_LIKE_NUM
from senior_bmark b, senior s, SENIOR_LIKE l
where b.key=s.key and s.key=l.key and b.member_id='test'
;


rollback;
commit;
    
    


--ȭ�鿡 ���̴� ���� �� ������ ǥ���ϱ�

SELECT  CASE WHEN DISTANCE < 1 THEN RTRIM(TO_CHAR(DISTANCE*1000 , 'FM9990D99'),'.')||'m' ELSE DISTANCE||'km' END distance,T.*
        , S.* ,  (SELECT COUNT(*) FROM SENIOR_LIKE L WHERE L.KEY = S.KEY ) SENIOR_LIKE_NUM
    from 
     (
        SELECT ROWNUM no, C.DISTANCE , S.KEY 
        FROM SENIOR S LEFT OUTER JOIN 
        (SELECT  KEY , DISTNACE_WGS84(35.1535583, 126.8879957, SENIOR_LATITUDE, SENIOR_LONGITUDE) AS DISTANCE
           FROM SENIOR S
      
        ) C ON S.KEY=C.KEY
        WHERE S.SENIOR_ROADADDRESS LIKE '%����%'
        ORDER BY C.DISTANCE ASC
    ) T INNER JOIN SENIOR S ON T.KEY = S.KEY
    WHERE T.NO < 20 ;

--���� 1
SELECT ROWNUM no, CASE WHEN DISTANCE < 1 THEN
		RTRIM(TO_CHAR(DISTANCE*1000 , 'FM9990D99'),'.')||'m' ELSE
		DISTANCE||'km' END distance,T.*
		, S.* , (SELECT SENIOR_LIKE_NUM FROM
		SENIOR_LIKE B WHERE B.KEY = S.KEY )
		SENIOR_LIKE_NUM
		from
		(
		SELECT ROWNUM
		no ,
		C.DISTANCE , S.KEY , S.SENIOR_ROADADDRESS
		FROM SENIOR S LEFT OUTER
		JOIN
		(SELECT KEY , DISTNACE_WGS84(35.1535583,
		 126.8879957,
		SENIOR_LATITUDE, SENIOR_LONGITUDE) AS DISTANCE
		FROM SENIOR S
		) C ON
		S.KEY=C.KEY
		ORDER BY C.DISTANCE ASC
		) T INNER JOIN
		SENIOR S ON T.KEY = S.KEY
--14 = 10
--17 =

select * from senior;

--�˻�
SELECT s.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS, s.SENIOR_NUMADDRESS, s.SENIOR_CALL, s.SENIOR_LATITUDE, s.SENIOR_LONGITUDE, s.SIDO, s.SIGUNGU
, l.SENIOR_LIKE_NUM
FROM SENIOR s
LEFT JOIN SENIOR_LIKE l
ON s.key = l.key
WHERE s.SENIOR_NAME LIKE '%����%' OR s.SENIOR_ROADADDRESS LIKE '%����%'
ORDER BY s.KEY;


--Ű���尡 2�� �̻��� ���
SELECT s.KEY, s.SENIOR_NAME,
       s.SENIOR_ROADADDRESS, s.SENIOR_NUMADDRESS, s.SENIOR_CALL,
       s.SENIOR_LATITUDE, s.SENIOR_LONGITUDE, s.SIDO, s.SIGUNGU,
       l.SENIOR_LIKE_NUM
FROM SENIOR s
LEFT JOIN SENIOR_LIKE l ON s.key = l.key
WHERE (
    s.SENIOR_NAME LIKE '%��ȣ%'
    OR s.SENIOR_ROADADDRESS LIKE '%��ȣ%'
)
AND (
    ' ' IS NULL
    OR s.SENIOR_NAME LIKE '%����%'
    OR s.SENIOR_ROADADDRESS LIKE '%����%'
)
ORDER BY s.KEY;






--'%'||#{keyword}||'%'


SELECT ROWNUM no, CASE WHEN DISTANCE < 1 THEN
		RTRIM(TO_CHAR(DISTANCE*1000 , 'FM9990D99'),'.')||'m' ELSE
		DISTANCE||'km' END distance,T.*
		, S.* , (SELECT COUNT(*) FROM
		SENIOR_BMARK B WHERE B.KEY = S.KEY )
		SENIOR_LIKE_NUM
		from
		(
		SELECT ROWNUM no ,
		C.DISTANCE , S.KEY , S.SENIOR_ROADADDRESS
		FROM SENIOR S LEFT OUTER JOIN
		(SELECT KEY , DISTNACE_WGS84(35.1534, 126.8880,
		SENIOR_LATITUDE, SENIOR_LONGITUDE) AS DISTANCE
		FROM SENIOR S
		) C ON
		S.KEY=C.KEY
		ORDER BY C.DISTANCE ASC
		) T INNER JOIN SENIOR S ON T.KEY = S.KEY
		 WHERE ROWNUM < 61;


select *
from location;


UPDATE LOCATION
SET MEMBER_ID = 'conan1', LOCATION_LATITUDE=11, LOCATION_LONGITUDE=11, LOCATION_TIME=sysdate
WHERE member_id='conan1';

rollback;

select to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS')
FROM DUAL;

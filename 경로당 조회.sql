--https://www.baraverkstad.se/encode/
--Ư������ ��ȯ ����Ʈ

--�����Ͱ� �ʹ� ���Ƽ� 25���� �켱 ���̰� �۾� ��
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

--��ü ��δ� ����Ʈ + ���ƿ�
select s.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS, s.SENIOR_NUMADDRESS, s.SENIOR_CALL, s.SENIOR_LATITUDE, s.SENIOR_LONGITUDE, s.SIDO, s.SIGUNGU
, l.SENIOR_LIKE_NUM
from SENIOR s
left join SENIOR_LIKE l
on s.key = l.key
where s.key < 25
order by key;

--���ƿ� �׽�Ʈ ����
insert into SENIOR_LIKE (MEMBER_ID, KEY, SENIOR_LIKE_NUM)
values ('admin', 13, 1);
commit;

--���ְ��� ��δ� ��ȸ
select * from SENIOR_LIKE
where member_id='admin';

select l.member_id, l.KEY, s.SENIOR_NAME, s.SENIOR_ROADADDRESS
from senior_like l
left join senior s
on l.key=s.key
where member_id='admin';


















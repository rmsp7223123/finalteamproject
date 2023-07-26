--KEY, SENIOR_NAME, SENIOR_ROADADDRESS, SENIOR_NUMADDRESS, SENIOR_CALL, SENIOR_LATITUDE, SENIOR_LONGITUDE

ALTER TABLE SENIOR ADD SIDO NVARCHAR2(15);
ALTER TABLE SENIOR ADD SIGUNGU NVARCHAR2(15);

--select addr1, regexp_substr(addr1, '[^ ]+', 1, 1) as sido from festival;
--select addr1, regexp_substr(addr1, '[^ ]+', 1, 2) as sigungu from festival;

SELECT regexp_substr(SENIOR_ROADADDRESS, '[^ ]+', 1, 1) as SIDO
FROM SENIOR;
SELECT SENIOR_ROADADDRESS, regexp_substr(SENIOR_ROADADDRESS, '[^ ]+', 1, 2) as SIDO
FROM SENIOR;


UPDATE SENIOR
SET SIDO = regexp_substr(SENIOR_ROADADDRESS, '[^ ]+', 1, 1) 
;

UPDATE SENIOR
SET SIGUNGU = regexp_substr(SENIOR_ROADADDRESS, '[^ ]+', 1, 2) 
;


INSERT INTO SENIOR(KEY, SENIOR_NAME, SENIOR_ROADADDRESS, SENIOR_NUMADDRESS, SENIOR_CALL, SENIOR_LATITUDE, SENIOR_LONGITUDE)
VALUES(1, '��ȣAPT��δ�', '���ֱ����� �ϱ� ��ȭ��� 33 (��ȭ�� ��ȣŸ��)'
, '���ֱ����� �ϱ� ��ȭ�� 471', '062-262-7342', '35.18317125', '126.9349668'
)
;

SELECT *
FROM SENIOR;

SELECT * FROM SENIOR_LIKE;

--���ƿ並 ������ ��
INSERT INTO SENIOR_LIKE(MEMBER_ID, KEY, SENIOR_LIKE_NUM)
VALUES('test', 102, 1);

--���ƿ� ��� ���� ��
DELETE FROM SENIOR_LIKE
WHERE KEY=102;

--��δ� key �� ���ƿ� �� ��ȸ
SELECT COUNT(*)
FROM SENIOR_LIKE
WHERE KEY=102;




COMMIT;
ROLLBACK;

SELECT * FROM MEMBER;

drop table senior_bmark;



CREATE TABLE SENIOR_BMARK (
	'Key'	number	primary key	NOT NULL,
	'member_id'	nvarchar2(15)		NOT NULL
);
ALTER TABLE senior_bmark ADD CONSTRAINT FK_member_TO_senior_bmark_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);



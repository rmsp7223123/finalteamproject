CREATE TABLE member (
	member_id	nvarchar2(15)	NOT NULL,
	member_pw	nvarchar2(18)	NOT NULL,
	member_name	nvarchar2(30)	NOT NULL,
    member_nickname	nvarchar2(30)	NOT NULL,
	member_age	number	NOT NULL,
	member_gender	nvarchar2(1)	NOT NULL check(member_gender in('남','여'))enable,
	member_phone	nvarchar2(15)	NOT NULL,
	member_profileimg	nvarchar2(2000)	NULL,
	member_phone_id	nvarchar2(300)	NULL,
	member_manner_score	number DEFAULT 50 NOT NULL,
    member_admin number default 0 not null
);

CREATE TABLE common1 (
	key1	number	NOT NULL,
	name	nvarchar2(10)	NOT NULL
);

CREATE TABLE common2 (
	key1	number	NOT NULL,
	key2	number	NOT NULL,
	name	nvarchar2(20)	NOT NULL
);

CREATE TABLE favor (
	favor	number	NOT NULL,
	member_id	nvarchar2(15)	NOT NULL
);

CREATE TABLE csboard (
	csboard_id	number	NOT NULL,
	writer	nvarchar2(15) NOT NULL, -- COMMENT '글쓴이'
	csboard_title	nvarchar2(50)	NOT NULL,
	csoboard_content	nvarchar2(2000)	NOT NULL,
	csboard_viewcount	number	NOT NULL,
	csboard_writedate	date DEFAULT sysdate NOT NULL
);

CREATE TABLE manner (
	manner_opponent_id	nvarchar2(15)	NOT NULL,
	member_id	nvarchar2(15)	NOT NULL,
	manner_score	number	 DEFAULT 50 NOT NULL	
);

CREATE TABLE call (
	member_id	nvarchar2(15)	NOT NULL,
	call_time	date	NOT NULL,
	call_opponenet_id	nvarchar2(15)	NOT NULL
);

CREATE TABLE option_table (
	member_id	nvarchar2(15)	NOT NULL,
	option_font_size	number	DEFAULT 20 NOT NULL	,
	option_font_color	nvarchar2(30)	DEFAULT '#000000' NOT NULL	,
	option_alarm	nvarchar2(3)	 DEFAULT 'N' check(option_alarm in('Y','N'))enable NOT NULL	,
	option_lock_pw	number	NULL,
	option_godok_alarm	nvarchar2(3)	DEFAULT 'N' check(option_godok_alarm in('Y','N'))enable NOT NULL	
);

CREATE TABLE csboard_comment (
	csboard_comment_id	number	NOT NULL,
	csboard_id	number	NOT NULL,
	member_id	nvarchar2(15)	NOT NULL,
	csboard_comment_writer	nvarchar2(15)	NOT NULL,
	csboard_comment_content	nvarchar2(1000)	NOT NULL,
	csboard_comment_writedate	date DEFAULT sysdate NOT NULL	
);

CREATE TABLE region (
	region	nvarchar2(20)	NOT NULL,
	member_id	nvarchar2(15)	NOT NULL,
	address	nvarchar2(100)	NULL
);

CREATE TABLE game (
	game_rank	number	NOT NULL,
	member_id	nvarchar2(15)	NOT NULL,
	game_score	number DEFAULT 0 NOT NULL	
);

CREATE TABLE senior (
	Key	number	NOT NULL,
	senior_name	nvarchar2(30)	NOT NULL,
	senior_roadaddress	nvarchar2(200)	NULL,
	senior_numaddress	nvarchar2(200)	NULL,
	senior_call	nvarchar2(20)	NULL,
	senior_latitude	nvarchar2(30)	NULL,
	senior_longitude	nvarchar2(30)	NULL
);

CREATE TABLE senior_like (
	member_id	nvarchar2(15)	NOT NULL,
	Key	number	NOT NULL,
	senior_like_num	number DEFAULT 0 NOT NULL	
);

CREATE TABLE dday (
	member_id	nvarchar2(15)	NOT NULL,
	dday_date	date	NOT NULL,
	dday_content	nvarchar2(300)	NOT NULL
);

CREATE TABLE ephone (
	member_id	nvarchar2(15)	NOT NULL,
	ephone_name	nvarchar2(15)	NOT NULL,
	ephone_phone	nvarchar2(15)	NOT NULL,
	ephone_relation	nvarchar2(30)	NULL,
	ephone_phone_id	nvarchar2(300)	NULL
);

CREATE TABLE location (
	member_id	nvarchar2(15)	NOT NULL,
	location_latitude	nvarchar2(30)	NOT NULL,
	location_longitude	nvarchar2(30)	NOT NULL,
	location_time	date DEFAULT sysdate	NULL	
);

CREATE TABLE fav_board (
	fav_board_id	number	NOT NULL,
	writer	nvarchar2(15)	NOT NULL,
	favor	number	NOT NULL,
	fav_board_title	nvarchar2(50)	NOT NULL,
	fav_board_content	nvarchar2(2000)	NOT NULL,
	fav_board_writedate	date DEFAULT sysdate	NOT NULL	,
	fav_board_writecount	number DEFAULT 0	NOT NULL	
);

CREATE TABLE fav_board_comment (
	fav_board_comment_id	number	NOT NULL,
	fav_board_id	number	NOT NULL,
	writer	nvarchar2(15)	NOT NULL,
	fav_board_comment_content	nvarchar2(1000)	NOT NULL,
	fav_board_comment_writedate	date DEFAULT sysdate	NOT NULL	,
	fav_board_comment_writer	nvarchar2(30)	NOT NULL
);

CREATE TABLE fav_board_like (
	member_id_like	nvarchar2(15)	NOT NULL,
	fav_board_id	number	NOT NULL
);

ALTER TABLE member ADD CONSTRAINT PK_MEMBER PRIMARY KEY (
	member_id
);

ALTER TABLE common1 ADD CONSTRAINT PK_COMMON1 PRIMARY KEY (
	key1
);

ALTER TABLE common2 ADD CONSTRAINT PK_COMMON2 PRIMARY KEY (
	key1
);

ALTER TABLE favor ADD CONSTRAINT PK_FAVOR PRIMARY KEY (
	favor,
	member_id
);

ALTER TABLE csboard ADD CONSTRAINT PK_CSBOARD PRIMARY KEY (
	csboard_id,
	writer
);

ALTER TABLE manner ADD CONSTRAINT PK_MANNER PRIMARY KEY (
	manner_opponent_id,
	member_id
);

ALTER TABLE call ADD CONSTRAINT PK_CALL PRIMARY KEY (
	member_id
);

ALTER TABLE option_table ADD CONSTRAINT PK_OPTION_TABLE PRIMARY KEY (
	member_id
);

ALTER TABLE csboard_comment ADD CONSTRAINT PK_CSBOARD_COMMENT PRIMARY KEY (
	csboard_comment_id,
	csboard_id,
	writer
);

ALTER TABLE region ADD CONSTRAINT PK_REGION PRIMARY KEY (
	region,
	member_id
);

ALTER TABLE game ADD CONSTRAINT PK_GAME PRIMARY KEY (
	game_rank,
	member_id
);

ALTER TABLE senior ADD CONSTRAINT PK_SENIOR PRIMARY KEY (
	Key
);

ALTER TABLE senior_like ADD CONSTRAINT PK_SENIOR_LIKE PRIMARY KEY (
	member_id,
	Key
);

ALTER TABLE dday ADD CONSTRAINT PK_DDAY PRIMARY KEY (
	member_id
);

ALTER TABLE ephone ADD CONSTRAINT PK_EPHONE PRIMARY KEY (
	member_id
);

ALTER TABLE location ADD CONSTRAINT PK_LOCATION PRIMARY KEY (
	member_id
);

ALTER TABLE fav_board ADD CONSTRAINT PK_FAV_BOARD PRIMARY KEY (
	fav_board_id,
	writer
);

ALTER TABLE fav_board_comment ADD CONSTRAINT PK_FAV_BOARD_COMMENT PRIMARY KEY (
	fav_board_comment_id,
	fav_board_id,
	writer
);

ALTER TABLE fav_board_like ADD CONSTRAINT PK_FAV_BOARD_LIKE PRIMARY KEY (
	member_id_like
);

ALTER TABLE common2 ADD CONSTRAINT FK_common1_TO_common2_1 FOREIGN KEY (
	key1
)
REFERENCES common1 (
	key1
);

ALTER TABLE favor ADD CONSTRAINT FK_member_TO_favor_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);

ALTER TABLE csboard ADD CONSTRAINT FK_member_TO_csboard_1 FOREIGN KEY (
	writer
)
REFERENCES member (
	member_id
);

ALTER TABLE manner ADD CONSTRAINT FK_member_TO_manner_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);

ALTER TABLE call ADD CONSTRAINT FK_member_TO_call_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);

ALTER TABLE option_table ADD CONSTRAINT FK_member_TO_option_table_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);

ALTER TABLE csboard_comment ADD CONSTRAINT FK_csboard_TO_csboard_comment_1 FOREIGN KEY (
	csboard_id
)
REFERENCES csboard (
	csboard_id
);

ALTER TABLE csboard_comment ADD CONSTRAINT FK_member_TO_csboard_comment_1 FOREIGN KEY (
	writer
)
REFERENCES member (
	member_id
);

ALTER TABLE region ADD CONSTRAINT FK_member_TO_region_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);

ALTER TABLE game ADD CONSTRAINT FK_member_TO_game_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);

ALTER TABLE senior_like ADD CONSTRAINT FK_member_TO_senior_like_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);

ALTER TABLE senior_like ADD CONSTRAINT FK_senior_TO_senior_like_1 FOREIGN KEY (
	Key
)
REFERENCES senior (
	Key
);

ALTER TABLE dday ADD CONSTRAINT FK_member_TO_dday_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);

ALTER TABLE ephone ADD CONSTRAINT FK_member_TO_ephone_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);

ALTER TABLE location ADD CONSTRAINT FK_member_TO_location_1 FOREIGN KEY (
	member_id
)
REFERENCES member (
	member_id
);

ALTER TABLE fav_board ADD CONSTRAINT FK_member_TO_fav_board_1 FOREIGN KEY (
	writer
)
REFERENCES member (
	member_id
);

ALTER TABLE fav_board_comment ADD CONSTRAINT FK_fav_board_TO_fav_board_comment_1 FOREIGN KEY (
	fav_board_id
)
REFERENCES fav_board (
	fav_board_id
);

ALTER TABLE fav_board_comment ADD CONSTRAINT FK_member_TO_fav_board_comment_1 FOREIGN KEY (
	writer
)
REFERENCES member (
	member_id
);

ALTER TABLE fav_board_like ADD CONSTRAINT FK_member_TO_fav_board_like_1 FOREIGN KEY (
	member_id_like
)
REFERENCES member (
	member_id
);

ALTER TABLE fav_board_like ADD CONSTRAINT FK_fav_board_TO_fav_board_like_1 FOREIGN KEY (
	fav_board_id
)
REFERENCES fav_board (
	fav_board_id
);


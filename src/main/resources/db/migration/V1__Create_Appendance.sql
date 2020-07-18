-- 勤怠テンプレートテーブル（マスタ管理）
create table attendance_template(
	id tinyint primary key not null auto_increment,
	work_id tinyint not null,
	start_hour_minute varchar(4) not null,
	end_hour_minute varchar(4) not null,
	rest float not null,
	over_work float not null,
	created_at timestamp not null,
	updated_at timestamp not null
);

-- 勤怠テーブル（トランザクション管理）
create table attendance(
	id smallint primary key not null auto_increment,
	year_and_month varchar(6) not null,
	day varchar(2) not null,
	start_hour_minute varchar(4) not null,
	end_hour_minute varchar(4) not null,
	rest float not null,
	over_work float not null,
	created_at timestamp not null,
	updated_at timestamp not null
);

-- 休日テーブル （マスタ管理）
create table holiday(
	id smallint primary key not null auto_increment,
	year_and_month varchar(6) not null,
	day varchar(2) not null,
	remark text,
	created_at timestamp not null,
	updated_at timestamp not null
);
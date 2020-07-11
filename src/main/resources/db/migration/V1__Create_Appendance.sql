-- 勤怠テンプレートテーブル（マスタ管理）
create table attendance_template(
	id tinyint primary key not null auto_increment,
	work_id tinyint not null,
	start_hour_minute smallint unsigned not null,
	end_hour_minute smallint unsigned not null,
	rest float not null,
	over_work float not null,
	created_at timestamp not null,
	updated_at timestamp not null
);

-- 勤怠テーブル --
create table attendance(
	id smallint primary key not null auto_increment,
	year_month_day int not null,
	start_hour_minute int not null,
	end_hour_minute int not null,
	rest float not null,
	over_work float not null,
	created_at timestamp not null,
	updated_at timestamp not null
);

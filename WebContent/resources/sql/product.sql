--product테이블 생성 코드
drop table product;

create table product(
	p_id varchar(10) not null,
	p_name varchar(20),
	p_unitPrice integer,
	p_description varchar(300),
	p_manufacturer varchar(20),
	p_category varchar(20),
	p_unitsInStock long,
	p_condition varchar(20),
	p_filename varchar(20),
	primary key(p_id)
);
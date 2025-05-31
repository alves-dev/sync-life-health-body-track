CREATE TABLE measurement (
   id bigint not null auto_increment,
   person_id varchar(30) not null,
   datetime DATETIME DEFAULT CURRENT_TIMESTAMP not null,
   measure varchar(25) not null,
   value double not null,
   primary key (id)
);
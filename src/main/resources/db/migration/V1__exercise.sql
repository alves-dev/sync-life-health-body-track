CREATE TABLE sleep_tracking (
   id bigint not null auto_increment,
   person_id varchar(30) not null,
   datetime DATETIME DEFAULT CURRENT_TIMESTAMP not null,
   action varchar(10) not null,
   primary key (id)
);
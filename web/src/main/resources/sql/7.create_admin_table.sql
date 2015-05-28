create table admin(
user_id varchar(255) not null,
name varchar(255) not null,
mobile_number bigint not null,
primary key (user_id));
commit;

ALTER TABLE admin
ADD CONSTRAINT admin_fk1
FOREIGN KEY (user_id)
REFERENCES admin_account(user_id);
commit;
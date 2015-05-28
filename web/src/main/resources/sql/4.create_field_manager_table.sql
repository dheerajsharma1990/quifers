create table field_executive(
user_id varchar(255) not null,
name varchar(255) not null,
mobile_number bigint not null,
primary key (user_id));
commit;

ALTER TABLE field_executive
ADD CONSTRAINT field_executive_fk1
FOREIGN KEY (user_id)
REFERENCES field_executive_account(user_id);
commit;
create table client
(order_id bigint,
name varchar(255),
mobile_number long,
email varchar(255));
commit;

alter table client
add constraint client_fk1
foreign key (order_id)
references orders(order_id);
commit;
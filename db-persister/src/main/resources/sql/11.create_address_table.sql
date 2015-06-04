create table address
(order_id bigint,
address_type varchar(100),
address_house_number varchar(255),
address_society varchar(255),
address_area varchar(255),
address_city varchar(255));
commit;

alter table address
add constraint address_fk1
foreign key (order_id)
references orders(order_id);
commit;
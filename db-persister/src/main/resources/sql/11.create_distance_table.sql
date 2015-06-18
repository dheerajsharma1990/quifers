create table distance
(order_id varchar(255),
distance integer);
commit;

alter table distance
add constraint distance_fk1
foreign key (order_id)
references orders(order_id);
commit;
create table price
(order_id bigint,
waiting_minutes integer,
transit_kilometres integer,
labours integer,
non_working_lifts integer);
commit;

alter table price
add constraint price_fk1
foreign key (order_id)
references orders(order_id);
commit;
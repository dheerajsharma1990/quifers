create table orders_workflow (order_id bigint,
order_state varchar(100),
effective_time timestamp,
primary key(order_id, order_state));
commit;
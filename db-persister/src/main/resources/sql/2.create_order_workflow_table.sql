create table orders_workflow (order_id varchar(255),
order_state varchar(100),
effective_time timestamp,
primary key(order_id, order_state));
commit;